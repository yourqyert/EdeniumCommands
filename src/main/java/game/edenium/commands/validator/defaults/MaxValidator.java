package game.edenium.commands.validator.defaults;

import game.edenium.commands.annotation.validation.Max;
import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.ValidationException;
import game.edenium.commands.validator.ArgumentValidator;

import java.lang.reflect.Parameter;

public final class MaxValidator implements ArgumentValidator {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(Max.class);
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

        long max = parameter.getAnnotation(Max.class).value();

        if (number.longValue() > max) {
            throw new ValidationException(
                    "Value must be at most than " + max + "."
            );
        }

    }

}