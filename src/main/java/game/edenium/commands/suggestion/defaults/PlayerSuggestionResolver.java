package game.edenium.commands.suggestion.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.suggestion.SuggestionResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

public final class PlayerSuggestionResolver
        implements SuggestionResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == Player.class;
    }

    @Override
    public List<String> resolve(
            CommandContext context,
            Parameter parameter
    ) {

        return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList());

    }

}