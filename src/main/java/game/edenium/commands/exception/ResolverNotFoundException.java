package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class ResolverNotFoundException extends CommandException {

    private final Class<?> type;

    public ResolverNotFoundException(Class<?> type) {
        super(
                MessageKey.NO_RESOLVER,
                Placeholder.unparsed("type", type.getName())
        );
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

}