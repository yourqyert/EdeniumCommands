package game.edenium.commands.suggestion;

import game.edenium.commands.context.CommandContext;
import java.lang.reflect.Parameter;
import java.util.List;

@FunctionalInterface
public interface SuggestionProvider {

    List<String> provide(
            CommandContext context,
            Parameter parameter
    );
}