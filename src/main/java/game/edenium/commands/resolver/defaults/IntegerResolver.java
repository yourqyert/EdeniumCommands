package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;

public final class IntegerResolver implements ArgumentResolver<Integer> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == int.class
                || parameter.getType() == Integer.class;
    }

    @Override
    public Integer resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new CommandException("'" + input + "' is not a valid integer.");
        }

    }

}