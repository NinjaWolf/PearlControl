package com.github.NinjaWolf.PearlControl;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PearlControl extends JavaPlugin {

    public static  Logger LOG = Logger.getLogger("PearlControl");
    private static PearlControl instance;


    public void onEnable() {

        // More Config Stuff
        FileConfiguration config = getConfig();

        instance = this;
        config.addDefault("PearlControl.Pearls.Disabled", false);
        config.addDefault("PearlControl.Pearls.Cooldown", 15);
        config.options().copyDefaults(true);
        saveConfig();

        getCommand("pearlcontrol").setExecutor(new PearlControlCommands());

        getServer().getPluginManager().registerEvents(new PearlControlListener(), this);
    }

    public void onDisable() {
        LOG.info("[PearlControl] Disabled");
    }

    public static PearlControl getInstance() {
        return instance;
    }
}
