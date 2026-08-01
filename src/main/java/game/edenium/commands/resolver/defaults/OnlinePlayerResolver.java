package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.InvalidArgumentException;
import game.edenium.commands.resolver.ArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;

public final class OnlinePlayerResolver
        implements ArgumentResolver<Player> {

    @Override
    public boolean supports(Parameter parameter) {
        return false;
    }

    @Override
    public Player resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) {

        Player player = Bukkit.getPlayerExact(input);

        if (player == null)
            throw new InvalidArgumentException(input);

        return player;

    }

}