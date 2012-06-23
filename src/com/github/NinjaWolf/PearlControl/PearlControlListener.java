package com.github.NinjaWolf.PearlControl;
 
import java.util.*;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
 
public class PearlControlListener implements Listener{
    PearlControl plugin;
 
    public PearlControlListener(PearlControl instance) {
        plugin = instance;
    }
    private Map<Player,Long> lastThrow = new HashMap<Player,Long>();
 
    @EventHandler
    public void onPlayerUseEP(PlayerInteractEvent e){
		if(e.getPlayer().hasPermission("pearlcontrol.pearls.disable")){
			return;
		}
    		if (plugin.getConfig().getBoolean("PearlControl.Pearls.Disabled") == true){
			if((e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL)){
			e.getPlayer().sendMessage(ChatColor.RED + "Ender Pearls are disabled.");
			e.setCancelled(true);}
    		}
    		 if (plugin.getConfig().getBoolean("PearlControl.Pearls.Disabled") == true) {
    	            return;
    	        }

	    if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK ) {
            return;
	    }
	    
        
        Long now = System.currentTimeMillis();
        Player player = e.getPlayer();
        	if(e.getPlayer().hasPermission("pearlcontrol.pearls.cooldown"))
        	{
        		return;
    }
            if((e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL  && !validthrow(player, now))){
                // Display message and cooldown time, cancel event.
                player.sendMessage(ChatColor.BLUE + "Enderpearl Cooling Down! " + remainingCooldown(player, now) + " Seconds Remaining.");
            	e.setCancelled(true);
            	}
            }
        // Remaining Cooldown time and Last throw check.
        private long remainingCooldown(Player player,long throwTime){
            Long lastPlayerPearl = lastThrow.get(player);
    	    final long cooldown = plugin.getConfig().getInt("PearlControl.Pearls.Cooldown") *1000;
            return (cooldown - (throwTime - lastPlayerPearl)) / 1000;
            }
   
        public boolean validthrow(Player player, long throwTime){
            Long lastPlayerPearl = lastThrow.get(player);
    	    final long cooldown = plugin.getConfig().getInt("PearlControl.Pearls.Cooldown") *1000;
            if (lastPlayerPearl == null || (throwTime - lastPlayerPearl) >= cooldown){
                lastThrow.put(player, throwTime);
                return true;
            } else { return false; }
        }
}