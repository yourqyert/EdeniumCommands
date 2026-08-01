package game.edenium.commands.scanner;

import game.edenium.commands.annotation.Command;
import game.edenium.commands.annotation.Default;
import game.edenium.commands.annotation.Greedy;
import game.edenium.commands.annotation.Optional;
import game.edenium.commands.annotation.Subcommand;
import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.descriptor.CommandMethod;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class ReflectionScanner {

    public CommandDescriptor scan(Object instance) {

        Class<?> type = instance.getClass();

        Command command = type.getAnnotation(Command.class);

        if (command == null) {
            throw new IllegalArgumentException(
                    type.getName() + " is not annotated with @Command."
            );
        }

        CommandDescriptor descriptor = new CommandDescriptor(
                command.value(),
                Arrays.asList(command.aliases())
        );

        boolean hasExecutableMethod = false;
        boolean hasDefault = false;

        Set<String> subcommands = new HashSet<>();

        for (Method method : type.getDeclaredMethods()) {

            boolean executable =
                    method.isAnnotationPresent(Default.class)
                            || method.isAnnotationPresent(Subcommand.class);

            if (!executable) {
                continue;
            }

            if (method.getReturnType() != void.class) {
                throw new IllegalStateException(
                        "Command method '" + method.getName() + "' must return void."
                );
            }

            boolean isDefault =
                    method.isAnnotationPresent(Default.class);

            boolean isSubcommand =
                    method.isAnnotationPresent(Subcommand.class);

            if (isDefault && isSubcommand) {
                throw new IllegalStateException(
                        "Method '" + method.getName()
                                + "' cannot have both @Default and @Subcommand."
                );
            }

            if (isDefault) {

                if (hasDefault) {
                    throw new IllegalStateException(
                            "Command '" + command.value()
                                    + "' already contains a @Default method."
                    );
                }

                hasDefault = true;

            }

            if (isSubcommand) {

                String path =
                        method.getAnnotation(Subcommand.class)
                                .value()
                                .trim()
                                .toLowerCase();

                if (!subcommands.add(path)) {
                    throw new IllegalStateException(
                            "Duplicate subcommand '" + path
                                    + "' in command '" + command.value() + "'."
                    );
                }

            }

            validateParameters(method);

            descriptor.addMethod(new CommandMethod(instance, method));

            hasExecutableMethod = true;

        }

        if (!hasExecutableMethod) {
            throw new IllegalStateException(
                    "Command '" + command.value()
                            + "' has no @Default or @Subcommand methods."
            );
        }

        return descriptor;

    }

    private void validateParameters(Method method) {

        Parameter[] parameters = method.getParameters();

        int senderCount = 0;
        int senderIndex = -1;

        boolean greedyFound = false;

        for (int i = 0; i < parameters.length; i++) {

            Parameter parameter = parameters[i];

            Class<?> type = parameter.getType();

            if (CommandSender.class.isAssignableFrom(type)) {

                senderCount++;

                if (senderIndex == -1) {
                    senderIndex = i;
                }

            }

            Optional optional =
                    parameter.getAnnotation(Optional.class);

            Greedy greedy =
                    parameter.getAnnotation(Greedy.class);

            if (optional != null
                    && type.isPrimitive()
                    && optional.value().isBlank()) {

                throw new IllegalStateException(
                        "@Optional on primitive parameter '"
                                + parameter.getName()
                                + "' in method '"
                                + method.getName()
                                + "' requires a default value."
                );

            }

            if (greedy != null) {

                if (greedyFound) {
                    throw new IllegalStateException(
                            "Method '" + method.getName()
                                    + "' can only contain one @Greedy parameter."
                    );
                }

                greedyFound = true;

                if (type != String.class) {
                    throw new IllegalStateException(
                            "@Greedy in method '"
                                    + method.getName()
                                    + "' can only be used on String."
                    );
                }

                if (optional != null) {
                    throw new IllegalStateException(
                            "@Greedy cannot be combined with @Optional."
                    );
                }

                if (i != parameters.length - 1) {
                    throw new IllegalStateException(
                            "@Greedy parameter in method '"
                                    + method.getName()
                                    + "' must be the last parameter."
                    );
                }

            }

        }

        if (senderCount > 1) {
            throw new IllegalStateException(
                    "Method '" + method.getName()
                            + "' can only have one CommandSender parameter."
            );
        }

        if (senderIndex > 0) {
            throw new IllegalStateException(
                    "CommandSender parameter in method '"
                            + method.getName()
                            + "' must be the first parameter."
            );
        }

    }

}