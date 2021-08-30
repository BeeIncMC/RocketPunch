package us.pixelgames.rocketpunch.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import us.pixelgames.rocketpunch.RocketPunch;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    private final HashMap<String, String> messages = new HashMap<>();

    public MessageManager(RocketPunch instance) {
        loadMessages(instance);
    }

    private void loadMessages(RocketPunch instance) {
        ConfigurationSection section = instance.getConfig().getConfigurationSection("messages");

        for (Map.Entry<String, Object> entry : section.getValues(false).entrySet()) {
            String rawVal = (String) entry.getValue();
            messages.put(entry.getKey(), ChatColor.translateAlternateColorCodes('&', rawVal));
        }
    }

    public String getMessage(String key) {
        return messages.getOrDefault(key, "");
    }
}