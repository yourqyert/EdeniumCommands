package game.edenium.commands.resolver;

import game.edenium.commands.context.CommandContext;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

public interface ArgumentResolver<T> {

    boolean supports(Parameter parameter);

    T resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) throws Exception;

    default List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {
        return Collections.emptyList();
    }

}