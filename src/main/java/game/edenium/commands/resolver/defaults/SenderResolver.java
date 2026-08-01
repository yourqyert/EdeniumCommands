package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.resolver.ArgumentResolver;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;

public final class SenderResolver
        implements ArgumentResolver<CommandSender> {

    @Override
    public boolean supports(Parameter parameter) {
        return CommandSender.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public CommandSender resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        return context.sender();

    }

}