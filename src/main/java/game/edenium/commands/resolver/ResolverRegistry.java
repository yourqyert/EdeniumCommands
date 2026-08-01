package game.edenium.commands.resolver;

import game.edenium.commands.context.CommandContext;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResolverRegistry {

    private final List<ArgumentResolver<?>> resolvers = new ArrayList<>();

    public void register(ArgumentResolver<?> resolver) {

        if (resolver == null) {
            throw new IllegalArgumentException("Resolver cannot be null.");
        }

        resolvers.add(resolver);

    }

    public void unregister(ArgumentResolver<?> resolver) {
        resolvers.remove(resolver);
    }

    public void clear() {
        resolvers.clear();
    }

    public List<ArgumentResolver<?>> resolvers() {
        return Collections.unmodifiableList(resolvers);
    }

    public ArgumentResolver<?> find(Parameter parameter) {

        for (ArgumentResolver<?> resolver : resolvers) {

            if (resolver.supports(parameter)) {
                return resolver;
            }

        }

        return null;

    }

    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {

        ArgumentResolver<?> resolver = find(parameter);

        if (resolver == null) {
            return Collections.emptyList();
        }

        return resolver.suggestions(
                context,
                parameter
        );

    }

}