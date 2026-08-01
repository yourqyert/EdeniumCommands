package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;

public final class FloatResolver implements ArgumentResolver<Float> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == float.class
                || parameter.getType() == Float.class;
    }

    @Override
    public Float resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException exception) {
            throw new CommandException("'" + input + "' is not a valid float.");
        }

    }

}