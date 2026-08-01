package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

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
            throw new CommandException(MessageKey.RESOLVER_INTEGER_NOT_VALID,
                    Placeholder.unparsed("input", input));
        }

    }

}