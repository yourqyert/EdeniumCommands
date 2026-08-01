package game.edenium.commands.util;

import game.edenium.commands.annotation.Greedy;
import game.edenium.commands.annotation.Optional;
import game.edenium.commands.descriptor.CommandMethod;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.StringJoiner;

public final class UsageBuilder {

    private UsageBuilder() {
    }

    public static String build(String label, CommandMethod method) {

        StringJoiner usage = new StringJoiner(" ");

        usage.add("/" + label);

        if (method.path().length != 0) {
            usage.add(String.join(" ", method.path()));
        }

        for (Parameter parameter : method.parameters()) {

            if (CommandSender.class.isAssignableFrom(parameter.getType())) {
                continue;
            }

            String name = parameter.getName();

            if (parameter.isAnnotationPresent(Greedy.class)) {
                name += "...";
            }

            if (parameter.isAnnotationPresent(Optional.class)) {
                usage.add("[" + name + "]");
            } else {
                usage.add("<" + name + ">");
            }

        }

        return usage.toString();
    }

}