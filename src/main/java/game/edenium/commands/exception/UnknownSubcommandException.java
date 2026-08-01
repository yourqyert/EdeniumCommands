package game.edenium.commands.exception;

public final class UnknownSubcommandException extends CommandException {

    public UnknownSubcommandException() {
        super("Unknown subcommand.");
    }

}