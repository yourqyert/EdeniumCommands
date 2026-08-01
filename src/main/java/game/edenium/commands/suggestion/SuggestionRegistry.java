package game.edenium.commands.suggestion;

import java.util.HashMap;
import java.util.Map;

public final class SuggestionRegistry {

    private final Map<String, SuggestionProvider> providers = new HashMap<>();

    public void register(String key, SuggestionProvider provider) {
        if (key == null || provider == null) {
            throw new IllegalArgumentException("Key and provider cannot be null.");
        }
        providers.put(key.toLowerCase(), provider);
    }

    public SuggestionProvider find(String key) {
        if (key == null) {
            return null;
        }
        return providers.get(key.toLowerCase());
    }

    public void unregister(String key) {
        if (key != null) {
            providers.remove(key.toLowerCase());
        }
    }

    public void clear() {
        providers.clear();
    }
}