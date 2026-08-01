package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.resolver.ArgumentResolver;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EnumResolver implements ArgumentResolver<Enum> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().isEnum();
    }

    @Override
    public Enum resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) throws Exception {

        Class<? extends Enum> type =
                (Class<? extends Enum>) parameter.getType();

        for (Enum constant : type.getEnumConstants()) {

            if (constant.name().equalsIgnoreCase(input)) {
                return constant;
            }

        }

        throw new CommandException(
                "Unknown " + type.getSimpleName() + ": " + input
        );

    }

    @Override
    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {

        Class<? extends Enum> type =
                (Class<? extends Enum>) parameter.getType();

        return Arrays.stream(type.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

    }

}