package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class ValidationException extends CommandException {
    public ValidationException(String message) {
        super(
                MessageKey.VALIDATION_ERROR,
                Placeholder.unparsed("error", message)
        );
    }
}
