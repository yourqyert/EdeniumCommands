package game.edenium.commands.exception;

import game.edenium.commands.context.CommandContext;
import game.edenium.commands.localization.LocalizationManager;
import game.edenium.commands.util.UsageBuilder;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

public final class DefaultExceptionHandler implements ExceptionHandler {

    private final LocalizationManager localization;

    public DefaultExceptionHandler(LocalizationManager localization) {
        this.localization = localization;
    }

    @Override
    public void handle(CommandContext context, CommandSender sender, Throwable throwable) {
        if (throwable instanceof CommandException exception) {
            TagResolver[] placeholders = exception.placeholders();

            if (context != null) {
                String usageString = UsageBuilder.build(context.label(), context.method());
                TagResolver usageResolver = Placeholder.unparsed("usage", usageString);

                TagResolver[] combined = new TagResolver[placeholders.length + 1];
                System.arraycopy(placeholders, 0, combined, 0, placeholders.length);
                combined[placeholders.length] = usageResolver;

                placeholders = combined;
            }

            sender.sendMessage(localization.getMessage(
                    exception.key(),
                    placeholders
            ));
            return;
        }
        throwable.printStackTrace();
    }
}