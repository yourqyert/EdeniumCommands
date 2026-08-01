package game.edenium.commands.validator.defaults;

import game.edenium.commands.annotation.validation.Range;
import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.ValidationException;
import game.edenium.commands.validator.ArgumentValidator;

import java.lang.reflect.Parameter;

public class RangeValidator implements ArgumentValidator {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(Range.class);
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

        long min = parameter.getAnnotation(Range.class).min();
        long max = parameter.getAnnotation(Range.class).max();

        if (number.longValue() < min || number.longValue() > max) {
            throw new ValidationException(
                    "Value must be in range between " + min + " and " + max
            );
        }

    }

}
