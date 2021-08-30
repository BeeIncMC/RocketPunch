package us.pixelgames.rocketpunch;

import org.bukkit.plugin.java.JavaPlugin;
import us.pixelgames.rocketpunch.listener.PlayerListener;
import us.pixelgames.rocketpunch.manager.CooldownManager;
import us.pixelgames.rocketpunch.manager.MessageManager;
import us.pixelgames.rocketpunch.manager.PermissionManager;

public final class RocketPunch extends JavaPlugin {
    private CooldownManager cooldownManager;
    private PermissionManager permissionManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        // Enable and copy the config
        this.reloadConfig();
        this.saveDefaultConfig();

        // Register Managers
        this.cooldownManager = new CooldownManager();
        this.permissionManager = new PermissionManager(this);
        this.messageManager = new MessageManager(this);

        // Enable listeners
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        this.cooldownManager = null;
        this.permissionManager = null;
        this.messageManager = null;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}