package game.edenium.commands.exception;

public final class InvalidArgumentException
        extends CommandException {

    public InvalidArgumentException(String argument) {

        super("Invalid argument: " + argument);

    }

}