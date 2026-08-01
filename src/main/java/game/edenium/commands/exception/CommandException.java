package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class CommandException extends RuntimeException {

    private final MessageKey key;
    private final TagResolver[] placeholders;

    public CommandException(MessageKey key, TagResolver... placeholders) {
        this.key = key;
        this.placeholders = placeholders;
    }

    public MessageKey key() {
        return key;
    }

    public TagResolver[] placeholders() {
        return placeholders;
    }
}