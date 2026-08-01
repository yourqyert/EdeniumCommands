package game.edenium.commands.localization;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.io.File;

public final class LocalizationManager {

    private LocalizationAdapter adapter;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public void init(File file, LocalizationAdapter adapter) {
        if (!adapter.supports(file)) {
            throw new IllegalArgumentException("The adapter does not support this file type.");
        }

        this.adapter = adapter;
        this.adapter.load(file, MessageKey.values());
    }

    public Component getMessage(MessageKey key, TagResolver... placeholders) {
        String rawMessage = adapter != null
                ? adapter.getMessage(key)
                : key.defaultMessage();

        return miniMessage.deserialize(rawMessage, placeholders);
    }
}