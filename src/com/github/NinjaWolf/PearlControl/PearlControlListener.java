package com.github.NinjaWolf.PearlControl;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PearlControlListener implements Listener {
	PearlControl plugin;
	
	public PearlControlListener(final PearlControl instance) {
		plugin = instance;
	}
	
	private final Map<Player, Long> lastThrow = new HashMap<Player, Long>();
	
	@EventHandler
	public void onPlayerUseEP(final PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("pearlcontrol.bypass.disable")) {
			return;
		}
		if (plugin.getConfig().getBoolean("PearlControl.Pearls.Disabled") == true) {
			if ((e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL)) {
				e.getPlayer().sendMessage(ChatColor.RED + "Ender Pearls are disabled.");
				e.setCancelled(true);
			}
		}
		if (plugin.getConfig().getBoolean("PearlControl.Pearls.Disabled") == true) {
			return;
		}
		
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			return;
		}
		
		final Long now = System.currentTimeMillis();
		final Player player = e.getPlayer();
		if (e.getPlayer().hasPermission("pearlcontrol.bypass.cooldown"))
		{
			return;
		}
		if ((e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL && !validthrow(player, now))) {
			// Display message and cooldown time, cancel event.
			player.sendMessage(ChatColor.BLUE + "Enderpearl Cooling Down! " + remainingCooldown(player, now) + " Seconds Remaining.");
			e.setCancelled(true);
		}
	}
	
	// Remaining Cooldown time and Last throw check.
	private long remainingCooldown(final Player player, final long throwTime) {
		final Long lastPlayerPearl = lastThrow.get(player);
		final long cooldown = plugin.getConfig().getInt("PearlControl.Pearls.Cooldown") * 1000;
		return (cooldown - (throwTime - lastPlayerPearl)) / 1000;
	}
	
	public boolean validthrow(final Player player, final long throwTime) {
		final Long lastPlayerPearl = lastThrow.get(player);
		final long cooldown = plugin.getConfig().getInt("PearlControl.Pearls.Cooldown") * 1000;
		if (lastPlayerPearl == null || (throwTime - lastPlayerPearl) >= cooldown) {
			lastThrow.put(player, throwTime);
			return true;
		} else {
			return false;
		}
	}
}
