package game.edenium.commands.resolver.defaults;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.exception.CommandException;
import game.edenium.commands.localization.MessageKey;
import game.edenium.commands.resolver.ArgumentResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

public final class LocationResolver implements ArgumentResolver<Location> {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType() == Location.class;
    }

    @Override
    public Location resolve(
            CommandContext context,
            Parameter parameter,
            String input
    ) throws Exception {
        String[] parts = input.split(" ");

        try {
            if (parts.length == 3) {
                if (!(context.sender() instanceof Player player)) {
                    throw new CommandException(MessageKey.RESOLVER_LOCATION_CONSOLE_USE_WORLD);
                }

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                double z = Double.parseDouble(parts[2]);

                return new Location(player.getWorld(), x, y, z);

            } else if (parts.length == 4) {
                World world = Bukkit.getWorld(parts[0]);
                if (world == null) {
                    throw new CommandException(MessageKey.RESOLVER_LOCATION_WORLD_NOT_FOUND,
                            Placeholder.unparsed("world", parts[0]));
                }

                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                return new Location(world, x, y, z);

            } else {
                throw new CommandException(MessageKey.RESOLVER_LOCATION_NOT_VALID);
            }
        } catch (NumberFormatException exception) {
            throw new CommandException(MessageKey.RESOLVER_LOCATION_COORDS_NOT_NUMBER,
                    Placeholder.unparsed("input", input));
            //throw new CommandException("Координаты '" + input + "' должны быть числами.");
        }
    }

    @Override
    public List<String> suggestions(
            CommandContext context,
            Parameter parameter
    ) {
        if (context.sender() instanceof Player player) {
            Location loc = player.getLocation();
            String suggestion = loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
            return List.of(suggestion);
        }

        return Collections.emptyList();
    }
}