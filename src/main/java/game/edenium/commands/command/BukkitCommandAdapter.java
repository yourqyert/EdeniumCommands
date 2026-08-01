package game.edenium.commands.command;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.descriptor.CommandMethod;
import game.edenium.commands.resolver.ResolverRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class BukkitCommandAdapter implements CommandExecutor, TabCompleter {

    private final CommandRegistry registry;
    private final ResolverRegistry resolvers;
    private final CommandDispatcher dispatcher;

    public BukkitCommandAdapter(
            CommandRegistry registry,
            ResolverRegistry resolvers,
            CommandDispatcher dispatcher
    ) {
        this.registry = registry;
        this.resolvers = resolvers;
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

        CommandMethod method = descriptor.find(args);

        if (method == null) {
            return descriptor.suggestSubcommands(args);
        }

        String[] remaining = method.isDefault()
                ? args
                : Arrays.copyOfRange(
                args,
                method.path().length,
                args.length
        );

        ParameterContext parameterContext =
                dispatcher.resolveParameter(
                        method,
                        remaining
                );

        if (parameterContext == null) {
            return Collections.emptyList();
        }

        Parameter parameter = parameterContext.parameter();

        CommandContext context = new CommandContext(
                sender,
                command,
                alias,
                remaining,
                descriptor,
                method
        );

        String current = remaining.length == 0
                ? ""
                : remaining[remaining.length - 1];

        return resolvers.suggestions(
                        context,
                        parameter
                ).stream()
                .filter(s -> s.regionMatches(
                        true,
                        0,
                        current,
                        0,
                        current.length()
                ))
                .collect(Collectors.toList());

    }

}