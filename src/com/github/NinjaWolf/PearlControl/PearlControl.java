package com.github.NinjaWolf.PearlControl;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PearlControl extends JavaPlugin {
	public final PearlControlListener pearlListener = new PearlControlListener(this);
	Logger log = Logger.getLogger("Minecraft");
		//Config Stuff
		String[] PearlControl = {"Config"};
		@Override
		public void onEnable(){ 
		
		// More Config Stuff
		FileConfiguration config = getConfig();

		config.addDefault("PearlControl.Pearls.Disabled", false);
		config.addDefault("PearlControl.Pearls.Cooldown", 15);
		
		config.options().copyDefaults(true);
		saveConfig();
				
		// Registering Events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(pearlListener , this);
		
		// Enabling Plugin
		log.info("[PearlControl] PearlControl is now Enabled.");

	}
	public void onDisable()
	{ 
		// Disabling Plugin
		log.info("[PearlControl] PearlControl is now Disabled.");
	}
	// Command stuff
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
	    Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("pearlcontrol")) {
            if (player == null) {
			reloadConfig();
            log.info("Reload succesful.");
			return true;
			}else if (player.hasPermission("pearlcontrol.reload")){
				 reloadConfig();
				 player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GREEN+"PearlControl"+ChatColor.DARK_RED+"] has been reloaded");
                 return true;
			 } else {
                 player.sendMessage(ChatColor.DARK_RED + "You don't have permission to do that!");
                 return true;
             } 
		}
		return false;
	}
}