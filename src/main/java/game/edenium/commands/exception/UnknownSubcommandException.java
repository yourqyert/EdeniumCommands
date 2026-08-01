package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class UnknownSubcommandException extends CommandException {

    public UnknownSubcommandException() {
        super(
                MessageKey.UNKNOWN_SUBCOMMAND
        );
    }

}