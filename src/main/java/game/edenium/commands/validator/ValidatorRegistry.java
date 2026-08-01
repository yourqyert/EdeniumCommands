package game.edenium.commands.validator;

import game.edenium.commands.context.CommandContext;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ValidatorRegistry {

    private final List<ArgumentValidator> validators = new ArrayList<>();

    public void register(ArgumentValidator validator) {
        validators.add(validator);
    }

    public void unregister(ArgumentValidator validator) {
        validators.remove(validator);
    }

    public void clear() {
        validators.clear();
    }

    public List<ArgumentValidator> validators() {
        return Collections.unmodifiableList(validators);
    }

    public List<ArgumentValidator> find(Parameter parameter) {

        List<ArgumentValidator> result = new ArrayList<>();

        for (ArgumentValidator validator : validators) {

            if (validator.supports(parameter)) {
                result.add(validator);
            }

        }

        return result;

    }

    public void validate(
            CommandContext context,
            Parameter parameter,
            Object value
    ) throws Exception {

        for (ArgumentValidator validator : find(parameter)) {

            validator.validate(
                    context,
                    parameter,
                    value
            );

        }

    }

}