package game.edenium.commands.exception;

import game.edenium.commands.localization.MessageKey;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.lang.reflect.Parameter;

public final class MissingArgumentException extends CommandException {

    private final Parameter parameter;

    public MissingArgumentException(Parameter parameter) {
        super(
                MessageKey.MISSING_ARGUMENT,
                Placeholder.unparsed("arg", parameter.getName())
        );
        this.parameter = parameter;
    }

    public Parameter parameter() {
        return parameter;
    }

}