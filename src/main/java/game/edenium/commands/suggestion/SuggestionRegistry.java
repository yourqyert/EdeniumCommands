package game.edenium.commands.suggestion;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public final class SuggestionRegistry {

    private final List<SuggestionResolver> resolvers =
            new ArrayList<>();

    public void register(SuggestionResolver resolver) {
        resolvers.add(resolver);
    }

    public SuggestionResolver find(Parameter parameter) {

        for (SuggestionResolver resolver : resolvers) {

            if (resolver.supports(parameter)) {
                return resolver;
            }

        }

        return null;

    }

}