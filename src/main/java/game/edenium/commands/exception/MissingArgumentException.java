package game.edenium.commands.exception;

import java.lang.reflect.Parameter;

public final class MissingArgumentException extends CommandException {

    private final Parameter parameter;

    public MissingArgumentException(Parameter parameter) {
        super("Missing argument: " + parameter.getName());
        this.parameter = parameter;
    }

    public Parameter parameter() {
        return parameter;
    }

}