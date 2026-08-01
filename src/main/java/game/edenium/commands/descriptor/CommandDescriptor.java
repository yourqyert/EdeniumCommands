package game.edenium.commands.descriptor;

import java.util.*;

public final class CommandDescriptor {

    private final String name;
    private final List<String> aliases;
    private final List<CommandMethod> methods = new ArrayList<>();

    private CommandMethod defaultMethod;

    public CommandDescriptor(String name, List<String> aliases) {
        this.name = name.toLowerCase();
        this.aliases = aliases == null
                ? List.of()
                : aliases.stream()
                .map(String::toLowerCase)
                .toList();
    }

    public String name() {
        return name;
    }

    public List<String> aliases() {
        return Collections.unmodifiableList(aliases);
    }

    public List<CommandMethod> methods() {
        return Collections.unmodifiableList(methods);
    }

    public CommandMethod defaultMethod() {
        return defaultMethod;
    }

    public void addMethod(CommandMethod method) {
        methods.add(method);

        if (method.isDefault()) {
            if (defaultMethod != null) {
                throw new IllegalStateException(
                        "Command '" + name + "' has more than one @Default method."
                );
            }

            defaultMethod = method;
        }
    }

    public CommandMethod find(String[] args) {

        CommandMethod best = defaultMethod;

        int longest = -1;

        for (CommandMethod method : methods) {

            if (method.isDefault()) {
                continue;
            }

            String[] path = method.path();

            if (args.length < path.length) {
                continue;
            }

            boolean matches = true;

            for (int i = 0; i < path.length; i++) {

                if (!path[i].equalsIgnoreCase(args[i])) {
                    matches = false;
                    break;
                }

            }

            if (matches && path.length > longest) {
                best = method;
                longest = path.length;
            }

        }

        return best;

    }

    public List<CommandMethod> findPartial(String[] args) {

        List<CommandMethod> methods = new ArrayList<>();

        for (CommandMethod method : this.methods) {

            if (method.isDefault()) {
                continue;
            }

            String[] path = method.path();

            if (args.length > path.length) {
                continue;
            }

            boolean matches = true;

            int length = Math.min(args.length, path.length);

            for (int i = 0; i < length; i++) {

                if (!path[i].toLowerCase().startsWith(args[i].toLowerCase())) {
                    matches = false;
                    break;
                }

            }

            if (matches) {
                methods.add(method);
            }

        }

        return methods;

    }

    public List<String> suggestSubcommands(String[] args) {

        Set<String> suggestions = new LinkedHashSet<>();

        for (CommandMethod method : methods) {

            if (method.isDefault()) {
                continue;
            }

            String[] path = method.path();

            if (args.length > path.length) {
                continue;
            }

            boolean matches = true;

            for (int i = 0; i < args.length - 1; i++) {

                if (!path[i].equalsIgnoreCase(args[i])) {
                    matches = false;
                    break;
                }

            }

            if (!matches) {
                continue;
            }

            String current = args.length == 0
                    ? ""
                    : args[args.length - 1];

            String suggestion = path[args.length - 1];

            if (suggestion
                    .toLowerCase()
                    .startsWith(current.toLowerCase())) {

                suggestions.add(suggestion);

            }

        }

        return new ArrayList<>(suggestions);

    }
}