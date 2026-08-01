package game.edenium.commands.exception;

public final class ResolverNotFoundException extends CommandException {

    private final Class<?> type;

    public ResolverNotFoundException(Class<?> type) {
        super("No resolver for " + type.getName());
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

}