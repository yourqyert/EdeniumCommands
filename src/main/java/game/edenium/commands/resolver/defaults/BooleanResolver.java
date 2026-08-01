package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

public final class BooleanResolver implements ArgumentResolver<Boolean> {

    private static final Set<String> TRUE = Set.of(
            "true", "yes", "on", "1"
    );

    private static final Set<String> FALSE = Set.of(
            "false", "no", "off", "0"
    );

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == boolean.class
                || parameter.getType() == Boolean.class;
    }

    @Override
    public Boolean resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        String value = input.toLowerCase();

        if (TRUE.contains(value))
            return true;

        if (FALSE.contains(value))
            return false;

        throw new CommandException("'" + input + "' is not a valid boolean.");
    }

    @Override
    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {
        return List.of("true", "false");
    }

}