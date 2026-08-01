package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class InvalidArgumentException
        extends CommandException {

    public InvalidArgumentException(String argument) {

        super(
                MessageKey.INVALID_ARGUMENT,
                Placeholder.unparsed("arg", argument)
        );

    }

}