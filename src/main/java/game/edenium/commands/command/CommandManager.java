package game.edenium.commands.command;

import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.exception.DefaultExceptionHandler;
import game.edenium.commands.exception.ExceptionHandler;
import game.edenium.commands.localization.LocalizationManager;
import game.edenium.commands.localization.adapters.YamlLocalizationAdapter;
import game.edenium.commands.resolver.ArgumentResolver;
import game.edenium.commands.resolver.ResolverRegistry;
import game.edenium.commands.resolver.defaults.*;
import game.edenium.commands.scanner.ReflectionScanner;
import game.edenium.commands.suggestion.SuggestionProvider;
import game.edenium.commands.suggestion.SuggestionRegistry;
import game.edenium.commands.validator.ValidatorRegistry;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class CommandManager {

    private final JavaPlugin plugin;
    private final ReflectionScanner scanner;
    private final CommandRegistry commandRegistry;
    private final ResolverRegistry resolverRegistry;
    private final ValidatorRegistry validatorRegistry;
    private final SuggestionRegistry suggestionRegistry;
    private final CommandDispatcher dispatcher;
    private final BukkitCommandAdapter adapter;
    private ExceptionHandler exceptionHandler;
    private LocalizationManager localizationManager;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scanner = new ReflectionScanner();
        this.commandRegistry = new CommandRegistry();
        this.resolverRegistry = new ResolverRegistry();
        this.validatorRegistry = new ValidatorRegistry();
        this.suggestionRegistry = new SuggestionRegistry();
        this.localizationManager = new LocalizationManager();

        registerDefaultResolvers();

        this.exceptionHandler = new DefaultExceptionHandler(localizationManager);
        this.dispatcher = new CommandDispatcher(
                commandRegistry,
                resolverRegistry,
                validatorRegistry,
                exceptionHandler
        );

        this.adapter = new BukkitCommandAdapter(
                commandRegistry,
                resolverRegistry,
                suggestionRegistry,
                dispatcher
        );

        File defaultLangFile = new File(plugin.getDataFolder(), "commands_messages.yml");
        this.localizationManager.init(defaultLangFile, new YamlLocalizationAdapter());
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
        resolverRegistry.register(new EnumResolver());
        resolverRegistry.register(new PlayerResolver());
        resolverRegistry.register(new UUIDResolver());
        resolverRegistry.register(new LocationResolver());
    }

    public void registerResolver(ArgumentResolver<?> resolver) {
        resolverRegistry.register(resolver);
    }

    public void registerSuggestion(String key, SuggestionProvider provider) {
        suggestionRegistry.register(key, provider);
    }

    public ExceptionHandler exceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
        dispatcher.setExceptionHandler(handler);
    }

    public LocalizationManager localizationManager() {
        return localizationManager;
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

    public SuggestionRegistry suggestions() {
        return suggestionRegistry;
    }

    public CommandDispatcher dispatcher() {
        return dispatcher;
    }
}