package game.edenium.commands.suggestion;

import game.edenium.commands.context.CommandContext;

import java.lang.reflect.Parameter;
import java.util.List;

public interface SuggestionResolver {

    boolean supports(Parameter parameter);

    List<String> resolve(
            CommandContext context,
            Parameter parameter
    );

}