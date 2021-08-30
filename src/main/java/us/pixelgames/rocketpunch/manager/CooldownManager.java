package us.pixelgames.rocketpunch.manager;

import us.pixelgames.rocketpunch.data.Cooldown;

import java.util.HashSet;
import java.util.UUID;

public class CooldownManager {
    private final HashSet<Cooldown> cooldowns = new HashSet<>();

    public void addCooldown(Cooldown cooldown) {
        cooldowns.add(cooldown);
    }

    public Cooldown getCooldownByUUIDAndName(UUID uuid, String cooldownName) {
        cooldowns.removeIf(cooldown1 -> cooldown1.getTimeRemaining() < 0);

        for (Cooldown cooldown : cooldowns) {
            if (cooldown.getUuid().equals(uuid)) {
                if (cooldown.getName().equals(cooldownName)) {
                    return cooldown;
                }
            }
        }
        return null;
    }
}