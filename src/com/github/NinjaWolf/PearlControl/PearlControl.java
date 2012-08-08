package com.github.NinjaWolf.PearlControl;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PearlControl extends JavaPlugin {
	public final PearlControlListener pearlListener = new PearlControlListener(this);
	Logger log = Logger.getLogger("Minecraft");
	//Config Stuff
	String[] PearlControl = {"Config"};
	
	@Override
	public void onEnable() {
		
		// More Config Stuff
		final FileConfiguration config = getConfig();
		
		config.addDefault("PearlControl.Pearls.Disabled", false);
		config.addDefault("PearlControl.Pearls.Cooldown", 15);
		config.options().copyDefaults(true);
		saveConfig();
		
		getCommand("pc").setExecutor(new PearlControlCommands(this));
		
		// Registering Events
		final PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(pearlListener, this);
		
		// Enabling Plugin
		log.info("[PearlControl] PearlControl is now Enabled.");
		
	}
	
	@Override
	public void onDisable()
	{
		// Disabling Plugin
		log.info("[PearlControl] PearlControl is now Disabled.");
	}
}
