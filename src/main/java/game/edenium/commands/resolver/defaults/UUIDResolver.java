package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class UUIDResolver implements ArgumentResolver<UUID> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == UUID.class;
    }

    @Override
    public UUID resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) throws Exception {

        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException exception) {
            throw new CommandException(MessageKey.RESOLVER_UUID_NOT_VALID,
                    Placeholder.unparsed("input", input));
        }

    }

    @Override
    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {
        return Collections.emptyList();
    }

}