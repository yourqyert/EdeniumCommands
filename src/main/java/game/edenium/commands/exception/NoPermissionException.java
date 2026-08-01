package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;

public final class NoPermissionException extends CommandException {

    private final String permission;

    public NoPermissionException(String permission) {
        super(
                MessageKey.NO_PERMISSION
        );
        this.permission = permission;
    }

    public String permission() {
        return permission;
    }

}