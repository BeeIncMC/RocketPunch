package us.pixelgames.rocketpunch.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import us.pixelgames.rocketpunch.RocketPunch;
import us.pixelgames.rocketpunch.data.Cooldown;

public class PlayerListener implements Listener {
    private final RocketPunch instance;

    public PlayerListener(RocketPunch instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPunch(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player hitter = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        if (victim.hasPermission(instance.getPermissionManager().getPermission("no-punch"))) {
            if (hitter.hasPermission(instance.getPermissionManager().getPermission("unlimited"))
                    || (hitter.hasPermission(instance.getPermissionManager().getPermission("limited"))))
                hitter.sendMessage(instance.getMessageManager().getMessage("no-punch"));
            return;
        }

        Cooldown limitedHitCooldown = instance.getCooldownManager().getCooldownByUUIDAndName(hitter.getUniqueId(), "limited");
        Cooldown unlimitedHitCooldown = instance.getCooldownManager().getCooldownByUUIDAndName(hitter.getUniqueId(), "unlimited");

        if (limitedHitCooldown != null) {
            if (limitedHitCooldown.getTimeRemaining() > 0) {
                hitter.sendMessage(instance.getMessageManager().getMessage("cooldown").replaceAll("%seconds%", "" + limitedHitCooldown.getTimeRemaining()));
                return;
            }
        }
        if (unlimitedHitCooldown != null) {
            if (unlimitedHitCooldown.getTimeRemaining() > 0) {
                hitter.sendMessage(instance.getMessageManager().getMessage("cooldown").replaceAll("%seconds%", "" + unlimitedHitCooldown.getTimeRemaining()));
                return;
            }
        }
        if (hitter.hasPermission(instance.getPermissionManager().getPermission("unlimited"))
                || (hitter.hasPermission(instance.getPermissionManager().getPermission("limited"))
                && victim.hasPermission(instance.getPermissionManager().getPermission("punchable")))) {
            victim.setVelocity(new Vector(0.025, instance.getConfig().getInt("punchdistance"), 0.025));
            victim.sendMessage(instance.getMessageManager().getMessage("success"));
            hitter.sendMessage(instance.getMessageManager().getMessage("success"));
            hitter.playSound(hitter.getLocation(), Sound.PISTON_EXTEND, 1f, 1f);
        }
        if (hitter.hasPermission(instance.getPermissionManager().getPermission("unlimited"))) {
            instance.getCooldownManager().addCooldown(new Cooldown(hitter.getUniqueId(), "unlimited", instance.getConfig().getConfigurationSection("cooldowns").getInt("unlimited")));
            return;
        }
        if (hitter.hasPermission(instance.getPermissionManager().getPermission("limited"))) {
            instance.getCooldownManager().addCooldown(new Cooldown(hitter.getUniqueId(), "limited", instance.getConfig().getConfigurationSection("cooldowns").getInt("limited")));
            return;
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPunch(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            event.setCancelled(true);
    }
}