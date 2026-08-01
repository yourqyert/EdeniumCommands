package game.edenium.commands.command;

import game.edenium.commands.annotation.Greedy;
import game.edenium.commands.annotation.Optional;
import game.edenium.commands.context.CommandContext;
import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.descriptor.CommandMethod;
import game.edenium.commands.exception.*;
import game.edenium.commands.resolver.ArgumentResolver;
import game.edenium.commands.resolver.ResolverRegistry;
import game.edenium.commands.validator.ValidatorRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.Arrays;

public final class CommandDispatcher {

    private final CommandRegistry commandRegistry;
    private final ResolverRegistry resolverRegistry;
    private final ValidatorRegistry validatorRegistry;

    private ExceptionHandler exceptionHandler;

    public CommandDispatcher(
            CommandRegistry commandRegistry,
            ResolverRegistry resolverRegistry,
            ValidatorRegistry validatorRegistry,
            ExceptionHandler exceptionHandler
    ) {

        this.commandRegistry = commandRegistry;
        this.resolverRegistry = resolverRegistry;
        this.validatorRegistry = validatorRegistry;
        this.exceptionHandler = exceptionHandler;

    }

    public void dispatch(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        CommandDescriptor descriptor = commandRegistry.find(label);

        if (descriptor == null) {
            return;
        }

        CommandMethod method = descriptor.find(args);

        if (method == null) {

            exceptionHandler.handle(
                    sender,
                    new UnknownSubcommandException()
            );

            return;

        }

        String[] remaining = method.isDefault()
                ? args
                : java.util.Arrays.copyOfRange(
                args,
                method.path().length,
                args.length
        );

        CommandContext context = new CommandContext(
                sender,
                command,
                label,
                remaining,
                descriptor,
                method
        );

        try {

            execute(context);

        } catch (Throwable throwable) {

            exceptionHandler.handle(
                    sender,
                    throwable
            );

        }

    }

    private void execute(CommandContext context) throws Throwable {

        CommandMethod method = context.method();

        if (method.hasPermission()
                && !context.sender().hasPermission(method.permission())) {

            throw new NoPermissionException(
                    method.permission()
            );

        }

        Object[] arguments = resolveArguments(context);

        method.handle().invokeWithArguments(arguments);

    }
    private Object[] resolveArguments(CommandContext context) throws Exception {

        Parameter[] parameters = context.method().parameters();

        Object[] resolved = new Object[parameters.length];

        int argumentIndex = 0;

        for (int parameterIndex = 0;
             parameterIndex < parameters.length;
             parameterIndex++) {

            Parameter parameter = parameters[parameterIndex];

            if (CommandSender.class.isAssignableFrom(parameter.getType())) {

                resolved[parameterIndex] = context.sender();
                continue;

            }

            ArgumentResolver<?> resolver = resolverRegistry.find(parameter);

            if (resolver == null) {
                throw new ResolverNotFoundException(
                        parameter.getType()
                );
            }

            if (parameter.isAnnotationPresent(Greedy.class)) {

                String value = "";

                if (argumentIndex < context.arguments().length) {

                    value = String.join(
                            " ",
                            Arrays.copyOfRange(
                                    context.arguments(),
                                    argumentIndex,
                                    context.arguments().length
                            )
                    );

                }

                Object resolvedValue = resolver.resolve(
                        context,
                        parameter,
                        value
                );

                validatorRegistry.validate(
                        context,
                        parameter,
                        resolvedValue
                );

                resolved[parameterIndex] = resolvedValue;

                break;

            }

            if (argumentIndex >= context.arguments().length) {

                Optional optional =
                        parameter.getAnnotation(Optional.class);

                if (optional == null) {
                    throw new MissingArgumentException(parameter);
                }

                if (optional.value().isBlank()) {

                    resolved[parameterIndex] = null;

                } else {

                    Object resolvedValue = resolver.resolve(
                            context,
                            parameter,
                            optional.value()
                    );

                    validatorRegistry.validate(
                            context,
                            parameter,
                            resolvedValue
                    );

                    resolved[parameterIndex] = resolvedValue;

                }

                continue;

            }

            Object resolvedValue = resolver.resolve(
                    context,
                    parameter,
                    context.arguments()[argumentIndex]
            );

            validatorRegistry.validate(
                    context,
                    parameter,
                    resolvedValue
            );

            resolved[parameterIndex] = resolvedValue;

            argumentIndex++;

        }

        return resolved;

    }
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public ParameterContext resolveParameter(
            CommandMethod method,
            String[] arguments
    ) {

        Parameter[] parameters = Arrays.stream(method.parameters())
                .filter(parameter ->
                        !CommandSender.class.isAssignableFrom(
                                parameter.getType()
                        )
                )
                .toArray(Parameter[]::new);

        if (parameters.length == 0) {
            return null;
        }

        int index = Math.max(0, arguments.length - 1);

        if (index < parameters.length) {
            return new ParameterContext(
                    parameters[index],
                    index
            );
        }

        Parameter last = parameters[parameters.length - 1];

        if (last.isAnnotationPresent(Greedy.class)) {
            return new ParameterContext(
                    last,
                    parameters.length - 1
            );
        }

        return null;

    }

}