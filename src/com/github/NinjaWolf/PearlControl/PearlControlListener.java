package com.github.NinjaWolf.PearlControl;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class PearlControlListener implements Listener {


    private HashMap<String, Long> lastThrow = new HashMap<String, Long>();
    public long cooldown = PearlControl.getInstance().getConfig().getInt("PearlControl.Pearls.Cooldown") * 1000;

    @EventHandler
    public void onPlayerUseEP(PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission("pearlcontrol.bypass.disable")) {
            return;
        }
        if (PearlControl.getInstance().getConfig().getBoolean("PearlControl.Pearls.Disabled")) {
            if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
                event.getPlayer().sendMessage(ChatColor.RED + "Ender Pearls are disabled.");
                event.setCancelled(true);
            }
        }
        if (PearlControl.getInstance().getConfig().getBoolean("PearlControl.Pearls.Disabled")) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Long now = System.currentTimeMillis();
        String player = event.getPlayer().getName();

        if (event.getPlayer().hasPermission("pearlcontrol.bypass.cooldown")) {
            return;
        }
        if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && !validthrow(player, now)) {
            // Display message and cooldown time, cancel event.
            event.getPlayer().sendMessage(ChatColor.BLUE + "Enderpearl Cooling Down! " + remainingCooldown(player, now) + " Seconds Remaining.");
            event.setCancelled(true);
        }
    }

    // Remaining Cooldown time and Last throw check.
    public long remainingCooldown(String player, long throwTime) {
        Long lastPlayerPearl = lastThrow.get(player);
        return (cooldown - (throwTime - lastPlayerPearl)) / 1000;
    }

    public boolean validthrow(String player, long throwTime) {
        Long lastPlayerPearl = lastThrow.get(player);

        if (lastPlayerPearl == null || (throwTime - lastPlayerPearl) >= cooldown) {
            lastThrow.put(player, throwTime);
            return true;
        } else
            return false;
    }
}
