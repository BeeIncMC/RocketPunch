package us.pixelgames.rocketpunch.data;

import java.util.UUID;

public class Cooldown {
    private final UUID uuid;
    private final String name;
    private final long startTime;
    private final int maxTime;

    public Cooldown(UUID uuid, String name, int maxTime) {
        this.uuid = uuid;
        this.name = name;
        this.startTime = System.currentTimeMillis();
        this.maxTime = maxTime;
    }

    public int getTimeRemaining() {
        return (int) (maxTime - ((System.currentTimeMillis() - this.startTime) / 1000));
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}