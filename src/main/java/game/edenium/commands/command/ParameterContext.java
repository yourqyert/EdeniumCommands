package game.edenium.commands.command;

import java.lang.reflect.Parameter;

public record ParameterContext(

        Parameter parameter,
        int argumentIndex

) {
}