package game.edenium.commands.validator;

import game.edenium.commands.context.CommandContext;

import java.lang.reflect.Parameter;

public interface ArgumentValidator {

    boolean supports(Parameter parameter);

    void validate(
            CommandContext context,
            Parameter parameter,
            Object value
    ) throws Exception;

}