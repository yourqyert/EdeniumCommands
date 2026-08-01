package game.edenium.commands.localization.adapters;

import game.edenium.commands.localization.LocalizationAdapter;
import game.edenium.commands.localization.MessageKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class YamlLocalizationAdapter implements LocalizationAdapter {

    private YamlConfiguration config;

    @Override
    public boolean supports(File file) {
        return file.getName().endsWith(".yml") || file.getName().endsWith(".yaml");
    }

    @Override
    public void load(File file, MessageKey[] keys) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException exception) {
                throw new RuntimeException("Failed to create localization file", exception);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
        boolean changed = false;

        for (MessageKey key : keys) {
            if (!config.contains(key.path())) {
                config.set(key.path(), key.defaultMessage());
                changed = true;
            }
        }

        if (changed) {
            try {
                config.save(file);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to save localization file", exception);
            }
        }
    }

    @Override
    public String getMessage(MessageKey key) {
        return config.getString(key.path(), key.defaultMessage());
    }
}