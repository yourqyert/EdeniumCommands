package game.edenium.commands.exception;

public final class NoResolverException extends CommandException {

    public NoResolverException(Class<?> type) {
        super("No ArgumentResolver registered for " + type.getName());
    }

}