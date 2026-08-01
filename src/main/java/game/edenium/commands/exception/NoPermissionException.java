package game.edenium.commands.exception;

public final class NoPermissionException extends CommandException {

    private final String permission;

    public NoPermissionException(String permission) {
        super("You don't have permission.");
        this.permission = permission;
    }

    public String permission() {
        return permission;
    }

}