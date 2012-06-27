package com.github.NinjaWolf.PearlControl;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PearlControlCommands
	extends JavaPlugin 
		implements CommandExecutor {
			PearlControl plugin;
		
	public PearlControlCommands(PearlControl instance){
		plugin = instance;}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(args.length == 0){
			sender.sendMessage(ChatColor.YELLOW + "----" + ChatColor.GOLD + " PearlControl " + ChatColor.YELLOW + "----");
			sender.sendMessage(ChatColor.GOLD + "Usage:" + ChatColor.WHITE + " /pc <command>");
			sender.sendMessage(ChatColor.GOLD + "/pc reload" + ChatColor.WHITE + " - Reloads the PearlControl Config.");
			sender.sendMessage(ChatColor.YELLOW + "----" + ChatColor.GOLD + " (1/1) " + ChatColor.YELLOW + "----");
			return true;
		}
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (args.length == 1){
			String arg1 = args[0];
			if (arg1.equalsIgnoreCase("reload")){
				if (player == null) {
					plugin.reloadConfig();
					plugin.log.info("[PearlControl]: Config Reloaded.");
					return true;
				}else if (player.hasPermission("pearlcontrol.reload")){
				plugin.reloadConfig();
				player.sendMessage("[" + ChatColor.GOLD + "PearlControl" + ChatColor.WHITE + "]:" + ChatColor.GOLD + " Config Reloaded!");
				return true;
			}
			player.sendMessage(ChatColor.RED + "You do not have access to this command!");
			}
			}
			return false;
	}
}