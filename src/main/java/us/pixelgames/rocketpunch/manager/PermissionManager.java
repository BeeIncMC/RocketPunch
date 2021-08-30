package us.pixelgames.rocketpunch.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import us.pixelgames.rocketpunch.RocketPunch;

import java.util.HashMap;
import java.util.Map;

public class PermissionManager {
    private final HashMap<String, Permission> permissions = new HashMap<>();

    public PermissionManager(RocketPunch instance) {
        loadMessages(instance);
    }

    private void loadMessages(RocketPunch instance) {
        ConfigurationSection section = instance.getConfig().getConfigurationSection("permissions");

        for (Map.Entry<String, Object> entry : section.getValues(false).entrySet()) {
            permissions.put(entry.getKey(), new Permission((String) entry.getValue()));
        }
    }

    public Permission getPermission(String key) {
        return permissions.get(key);
    }
}