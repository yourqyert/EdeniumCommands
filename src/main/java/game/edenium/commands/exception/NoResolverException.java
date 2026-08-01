package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class NoResolverException extends CommandException {

    public NoResolverException(Class<?> type) {
        super(
                MessageKey.NO_ARGUMENT_RESOLVER,
                Placeholder.unparsed("type", type.getName())
        );
    }

}