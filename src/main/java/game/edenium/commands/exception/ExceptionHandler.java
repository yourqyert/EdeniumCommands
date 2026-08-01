package game.edenium.commands.exception;

import game.edenium.commands.context.CommandContext;
import org.bukkit.command.CommandSender;

public interface ExceptionHandler {
    void handle(
            CommandContext context,
            CommandSender sender,
            Throwable throwable
    );
}