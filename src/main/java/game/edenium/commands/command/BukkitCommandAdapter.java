package game.edenium.commands.command;

import game.edenium.commands.annotation.suggestions.Suggest;
import game.edenium.commands.annotation.suggestions.Suggestions;
import game.edenium.commands.context.CommandContext;
import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.descriptor.CommandMethod;
import game.edenium.commands.resolver.ArgumentResolver;
import game.edenium.commands.resolver.ResolverRegistry;
import game.edenium.commands.suggestion.SuggestionProvider;
import game.edenium.commands.suggestion.SuggestionRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BukkitCommandAdapter implements CommandExecutor, TabCompleter {

    private final CommandRegistry registry;
    private final ResolverRegistry resolvers;
    private final SuggestionRegistry suggestions;
    private final CommandDispatcher dispatcher;

    public BukkitCommandAdapter(
            CommandRegistry registry,
            ResolverRegistry resolvers,
            SuggestionRegistry suggestions,
            CommandDispatcher dispatcher
    ) {
        this.registry = registry;
        this.resolvers = resolvers;
        this.suggestions = suggestions;
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        dispatcher.dispatch(
                sender,
                command,
                label,
                args
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        CommandDescriptor descriptor = registry.find(alias);
        if (descriptor == null) {
            return Collections.emptyList();
        }

        List<String> rawSuggestions = new java.util.ArrayList<>();

        rawSuggestions.addAll(descriptor.suggestSubcommands(sender, args));

        CommandMethod method = descriptor.find(args);

        if (method != null && (!method.hasPermission() || sender.hasPermission(method.permission()))) {
            String[] remaining = method.isDefault()
                    ? args
                    : Arrays.copyOfRange(args, method.path().length, args.length);

            ParameterContext parameterContext = dispatcher.resolveParameter(method, remaining);
            if (parameterContext != null) {
                CommandContext context = new CommandContext(
                        sender,
                        command,
                        alias,
                        remaining,
                        descriptor,
                        method
                );
                rawSuggestions.addAll(resolveSuggestions(context, parameterContext.parameter()));
            }
        }

        return filterByInput(rawSuggestions, args);
    }

    private List<String> resolveSuggestions(CommandContext context, Parameter parameter) {
        String suggestionKey = extractSuggestionKey(parameter);
        if (suggestionKey != null) {
            SuggestionProvider provider = suggestions.find(suggestionKey);
            if (provider != null) {
                List<String> provided = provider.provide(context, parameter);
                if (provided != null && !provided.isEmpty()) {
                    return provided;
                }
            }
        }

        ArgumentResolver<?> resolver = resolvers.find(parameter);
        if (resolver != null) {
            List<String> resolved = resolver.suggestions(context, parameter);
            if (resolved != null && !resolved.isEmpty()) {
                return resolved;
            }
        }

        return Collections.emptyList();
    }

    private String extractSuggestionKey(Parameter parameter) {
        if (parameter.isAnnotationPresent(Suggest.class)) {
            return parameter.getAnnotation(Suggest.class).value();
        }
        if (parameter.isAnnotationPresent(Suggestions.class)) {
            return parameter.getAnnotation(Suggestions.class).value();
        }
        return null;
    }

    private List<String> filterByInput(List<String> rawSuggestions, String[] args) {
        if (rawSuggestions == null || rawSuggestions.isEmpty()) {
            return Collections.emptyList();
        }

        String currentInput = args.length == 0 ? "" : args[args.length - 1];
        if (currentInput.isBlank()) {
            return rawSuggestions;
        }

        return rawSuggestions.stream()
                .filter(suggestion -> suggestion.regionMatches(
                        true,
                        0,
                        currentInput,
                        0,
                        currentInput.length()
                ))
                .toList();
    }
}