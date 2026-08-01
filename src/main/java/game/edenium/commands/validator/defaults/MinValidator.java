package game.edenium.commands.validator.defaults;

import game.edenium.commands.annotation.validation.Min;
import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.ValidationException;
import game.edenium.commands.validator.ArgumentValidator;

import java.lang.reflect.Parameter;

public final class MinValidator implements ArgumentValidator {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(Min.class);
    }

    @Override
    public void validate(
            CommandContext context,
            Parameter parameter,
            Object value
    ) {

        if (!(value instanceof Number number)) {
            return;
        }

        long min = parameter.getAnnotation(Min.class).value();

        if (number.longValue() < min) {
            throw new ValidationException(
                    "Value must be at least " + min + "."
            );
        }

    }

}