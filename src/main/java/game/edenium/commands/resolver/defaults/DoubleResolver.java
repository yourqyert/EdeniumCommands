package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.lang.reflect.Parameter;

public final class DoubleResolver implements ArgumentResolver<Double> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == double.class
                || parameter.getType() == Double.class;
    }

    @Override
    public Double resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException exception) {
            throw new CommandException(MessageKey.RESOLVER_DOUBLE_NOT_VALID,
                    Placeholder.unparsed("input", input));
        }

    }

}