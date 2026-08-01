package game.edenium.commands.descriptor;

import game.edenium.commands.annotation.Default;
import game.edenium.commands.annotation.Permission;
import game.edenium.commands.annotation.Subcommand;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public final class CommandMethod {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Object instance;
    private final Method method;
    private final Parameter[] parameters;

    private final String[] path;
    private final boolean defaultCommand;
    private final String permission;

    private MethodHandle handle;

    public CommandMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.parameters = method.getParameters();

        this.defaultCommand = method.isAnnotationPresent(Default.class);

        Subcommand subcommand = method.getAnnotation(Subcommand.class);
        this.path = subcommand == null
                ? new String[0]
                : Arrays.stream(subcommand.value().trim().split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .toArray(String[]::new);

        Permission permission = method.getAnnotation(Permission.class);
        this.permission = permission == null ? null : permission.value();

        method.setAccessible(true);
    }

    public Object instance() {
        return instance;
    }

    public Method method() {
        return method;
    }

    public Parameter[] parameters() {
        return parameters;
    }

    public String[] path() {
        return path;
    }

    public boolean isDefault() {
        return defaultCommand;
    }

    public String permission() {
        return permission;
    }

    public boolean hasPermission() {
        return permission != null && !permission.isBlank();
    }

    public MethodHandle handle() {
        if (handle == null) {
            try {
                handle = LOOKUP.unreflect(method).bindTo(instance);
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
        return handle;
    }
}