package me.silentprogram.advancedboats.commands;

import me.silentprogram.advancedboats.AdvancedBoats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
	AdvancedBoats plugin;
	
	public TestCommand(AdvancedBoats plugin) {
		this.plugin = plugin;
		plugin.getCommand("test").setExecutor(this);
		System.out.println("Registered Command!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player plr = (Player) sender;
		ArmorStand as = plr.getLocation().getWorld().spawn(plr.getLocation(), ArmorStand.class);
		as.addPassenger(plr);
		return true;
	}
}
