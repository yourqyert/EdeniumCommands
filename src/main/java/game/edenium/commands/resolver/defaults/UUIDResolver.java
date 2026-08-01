package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

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
            throw new CommandException(
                    "'" + input + "' is not a valid UUID."
            );
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