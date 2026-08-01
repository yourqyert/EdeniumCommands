package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.List;

public final class PlayerResolver implements ArgumentResolver<Player> {

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

        Player player = Bukkit.getPlayer(input);

        if (player == null) {
            throw new CommandException(MessageKey.RESOLVER_PLAYER_NOT_FOUND,
                    Placeholder.unparsed("input", input));
        }

        return player;
    }

    @Override
    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {

        return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .toList();

    }

}