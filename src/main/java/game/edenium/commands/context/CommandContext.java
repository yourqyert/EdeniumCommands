package game.edenium.commands.context;

import game.edenium.commands.descriptor.CommandDescriptor;
import game.edenium.commands.descriptor.CommandMethod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class CommandContext {

    private final CommandSender sender;

    private final Command command;

    private final String label;

    private final String[] arguments;

    private final CommandDescriptor descriptor;

    private final CommandMethod method;

    public CommandContext(
            CommandSender sender,
            Command command,
            String label,
            String[] arguments,
            CommandDescriptor descriptor,
            CommandMethod method
    ) {

        this.sender = sender;
        this.command = command;
        this.label = label;
        this.arguments = arguments;
        this.descriptor = descriptor;
        this.method = method;

    }

    public CommandSender sender() {
        return sender;
    }

    public Command command() {
        return command;
    }

    public String label() {
        return label;
    }

    public String[] arguments() {
        return arguments;
    }

    public CommandDescriptor descriptor() {
        return descriptor;
    }

    public CommandMethod method() {
        return method;
    }

}