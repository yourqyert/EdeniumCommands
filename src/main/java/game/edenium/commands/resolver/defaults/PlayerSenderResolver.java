package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.exception.InvalidArgumentException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;

public final class PlayerSenderResolver
        implements ArgumentResolver<Player> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == Player.class;
    }

    @Override
    public Player resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        if (context.sender() instanceof Player player)
            return player;

        throw new CommandException(MessageKey.PLAYER_ONLY);

    }

}