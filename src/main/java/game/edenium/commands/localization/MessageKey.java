package game.edenium.commands.localization;

public enum MessageKey {

    NO_PERMISSION("messages.no-permission", "<red>You do not have permission."),
    UNKNOWN_SUBCOMMAND("messages.unknown-subcommand", "<red>Unknown subcommand."),
    MISSING_ARGUMENT("messages.missing-argument", "<red>Missing argument: <arg><newline><gray>Usage: <usage>"),
    INVALID_BOOLEAN("messages.invalid-boolean", "<red>'<value>' is not a valid boolean.<newline><gray>Usage: <usage>"),
    INVALID_NUMBER("messages.invalid-number", "<red>'<value>' is not a valid number.<newline><gray>Usage: <usage>"),
    PLAYER_NOT_FOUND("messages.player-not-found", "<red>Player '<input>' was not found."),
    PLAYER_ONLY("messages.player-only", "<red>This command can only be executed by a player."),
    INVALID_LOCATION_FORMAT("messages.invalid-location-format", "<red>Invalid location format. Use x,y,z"),
    VALIDATION_ERROR("messages.validation-error", "<red><error><newline><gray>Usage: <usage>"),
    INVALID_ARGUMENT("messages.invalid-argument", "<red>Invalid argument: <arg><newline><gray>Usage: <usage>"),
    NO_ARGUMENT_RESOLVER("messages.no-argument-resolver", "<red>No ArgumentResolver registered for type <type>."),
    NO_RESOLVER("messages.no-resolver", "<red>No Resolver registered for type <type>."),
    RESOLVER_BOOLEAN_NOT_VALID("messages.resolvers.boolean.not-valid", "<red>'<input>' is not a valid boolean.<newline><gray>Usage: <usage>"),
    RESOLVER_ENUM_UNKNOWN("messages.resolvers.enum.unknown-enum", "<red>Unknown <type>: <input><newline><gray>Usage: <usage>"),
    RESOLVER_DOUBLE_NOT_VALID("messages.resolvers.double.not-valid", "<red>'<input>' is not a valid double.<newline><gray>Usage: <usage>"),
    RESOLVER_FLOAT_NOT_VALID("messages.resolvers.float.not-valid", "<red>'<input>' is not a valid float.<newline><gray>Usage: <usage>"),
    RESOLVER_INTEGER_NOT_VALID("messages.resolvers.integer.not-valid", "<red>'<input>' is not a valid integer.<newline><gray>Usage: <usage>"),
    RESOLVER_LOCATION_CONSOLE_USE_WORLD("messages.resolvers.location.console-use-world", "<red>Console must specify a world: world x y z"),
    RESOLVER_LOCATION_WORLD_NOT_FOUND("messages.resolvers.location.world-not-found", "<red>World '<world>' was not found."),
    RESOLVER_LOCATION_COORDS_NOT_NUMBER("messages.resolvers.location.coords-not-number", "<red>Coordinates '<input>' must be valid numbers."),
    RESOLVER_LOCATION_NOT_VALID("messages.resolvers.location.not-valid", "<red>Invalid location format. Use 'x y z' or 'world x y z'."),
    RESOLVER_UUID_NOT_VALID("messages.resolvers.uuid.not-valid", "<red>UUID '<input>' is not valid.<newline><gray>Usage: <usage>"),
    RESOLVER_LONG_NOT_VALID("messages.resolvers.long.not-valid", "<red>'<input>' is not a valid long.<newline><gray>Usage: <usage>"),
    RESOLVER_PLAYER_NOT_FOUND("messages.resolvers.player.not-valid", "<red>'<input>' is not a valid player.<newline><gray>Usage: <usage>");

    private final String path;
    private final String defaultMessage;

    MessageKey(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    public String path() {
        return path;
    }

    public String defaultMessage() {
        return defaultMessage;
    }
}