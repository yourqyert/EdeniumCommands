package game.edenium.commands.exception;

import org.bukkit.command.CommandSender;

public final class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(CommandSender sender, Throwable throwable) {

        if (throwable instanceof CommandException exception) {
            sender.sendMessage("§c" + exception.getMessage());
            return;
        }

        throwable.printStackTrace();

    }

}