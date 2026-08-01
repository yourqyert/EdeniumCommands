package game.edenium.commands.exception;

import org.bukkit.command.CommandSender;

public interface ExceptionHandler {

    void handle(
            CommandSender sender,
            Throwable throwable
    );

}