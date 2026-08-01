package game.edenium.commands.localization;

import java.io.File;

public interface LocalizationAdapter {
    boolean supports(File file);

    void load(File file, MessageKey[] keys);

    String getMessage(MessageKey key);
}