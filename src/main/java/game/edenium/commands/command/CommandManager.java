package game.edenium.commands.command;

import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.exception.DefaultExceptionHandler;
import game.edenium.commands.exception.ExceptionHandler;
import game.edenium.commands.resolver.ArgumentResolver;
import game.edenium.commands.resolver.ResolverRegistry;
import game.edenium.commands.resolver.defaults.*;
import game.edenium.commands.scanner.ReflectionScanner;
import game.edenium.commands.suggestion.SuggestionRegistry;
import game.edenium.commands.validator.ValidatorRegistry;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CommandManager {

    private final JavaPlugin plugin;

    private final ReflectionScanner scanner;
    private final CommandRegistry commandRegistry;
    private final ResolverRegistry resolverRegistry;
    private final ValidatorRegistry validatorRegistry;
    private final CommandDispatcher dispatcher;
    private final BukkitCommandAdapter adapter;
    private final SuggestionRegistry suggestionRegistry;

    private ExceptionHandler exceptionHandler;

    public CommandManager(JavaPlugin plugin) {

        this.plugin = plugin;

        this.scanner = new ReflectionScanner();
        this.commandRegistry = new CommandRegistry();
        this.resolverRegistry = new ResolverRegistry();
        this.validatorRegistry = new ValidatorRegistry();

        registerDefaultResolvers();

        this.exceptionHandler = new DefaultExceptionHandler();

        this.dispatcher = new CommandDispatcher(
                commandRegistry,
                resolverRegistry,
                validatorRegistry,
                exceptionHandler
        );

        this.suggestionRegistry = new SuggestionRegistry();

        this.adapter = new BukkitCommandAdapter(
                commandRegistry,
                resolverRegistry,
                suggestionRegistry,
                dispatcher
        );

    }

    public void register(Object command) {

        CommandDescriptor descriptor = scanner.scan(command);

        commandRegistry.register(descriptor);

        PluginCommand pluginCommand = Objects.requireNonNull(
                plugin.getCommand(descriptor.name()),
                "Command '" + descriptor.name() + "' is missing in plugin.yml."
        );

        pluginCommand.setExecutor(adapter);
        pluginCommand.setTabCompleter(adapter);

    }

    private void registerDefaultResolvers() {

        resolverRegistry.register(new StringResolver());
        resolverRegistry.register(new IntegerResolver());
        resolverRegistry.register(new DoubleResolver());
        resolverRegistry.register(new FloatResolver());
        resolverRegistry.register(new LongResolver());
        resolverRegistry.register(new BooleanResolver());
        resolverRegistry.register(new PlayerResolver());
        resolverRegistry.register(new UUIDResolver());
        resolverRegistry.register(new EnumResolver());

    }

    public void registerResolver(ArgumentResolver<?> resolver) {
        resolverRegistry.register(resolver);
    }

    public ExceptionHandler exceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler handler) {

        this.exceptionHandler = handler;
        dispatcher.setExceptionHandler(handler);

    }

    public ReflectionScanner scanner() {
        return scanner;
    }

    public CommandRegistry registry() {
        return commandRegistry;
    }

    public ResolverRegistry resolvers() {
        return resolverRegistry;
    }

    public CommandDispatcher dispatcher() {
        return dispatcher;
    }

}