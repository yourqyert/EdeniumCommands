package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;

public final class LongResolver implements ArgumentResolver<Long> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == long.class
                || parameter.getType() == Long.class;
    }

    @Override
    public Long resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        try {
            return Long.parseLong(input);
        } catch (NumberFormatException exception) {
            throw new CommandException("'" + input + "' is not a valid long.");
        }

    }

}