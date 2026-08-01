package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

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
            throw new CommandException(MessageKey.RESOLVER_FLOAT_NOT_VALID,
                    Placeholder.unparsed("input", input));
        }

    }

}