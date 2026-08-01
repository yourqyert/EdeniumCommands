package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;

public final class StringResolver implements ArgumentResolver<String> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == String.class;
    }

    @Override
    public String resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {
        return input;
    }

}