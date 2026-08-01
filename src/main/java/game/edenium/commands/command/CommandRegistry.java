package game.edenium.commands.command;

import game.edenium.commands.descriptor.CommandDescriptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CommandRegistry {

    private final Map<String, CommandDescriptor> commands = new HashMap<>();

    public void register(CommandDescriptor descriptor) {

        register0(descriptor.name(), descriptor);

        for (String alias : descriptor.aliases()) {
            register0(alias, descriptor);
        }

    }

    private void register0(String name, CommandDescriptor descriptor) {

        String key = name.toLowerCase();

        if (commands.containsKey(key)) {
            throw new IllegalStateException(
                    "Command '" + key + "' is already registered."
            );
        }

        commands.put(key, descriptor);

    }

    public CommandDescriptor find(String name) {

        if (name == null) {
            return null;
        }

        return commands.get(name.toLowerCase());

    }

    public boolean contains(String name) {

        if (name == null) {
            return false;
        }

        return commands.containsKey(name.toLowerCase());

    }

    public void unregister(String name) {

        if (name == null) {
            return;
        }

        CommandDescriptor descriptor = commands.remove(name.toLowerCase());

        if (descriptor == null) {
            return;
        }

        commands.values().removeIf(value -> value == descriptor);

    }

    public void clear() {
        commands.clear();
    }

    public Collection<CommandDescriptor> descriptors() {
        return Collections.unmodifiableCollection(commands.values());
    }

}