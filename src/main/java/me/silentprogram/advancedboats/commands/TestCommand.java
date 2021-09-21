package me.silentprogram.advancedboats.commands;

import me.silentprogram.advancedboats.AdvancedBoats;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestCommand implements CommandExecutor {
	AdvancedBoats plugin;
	
	public TestCommand(AdvancedBoats plugin) {
		this.plugin = plugin;
		plugin.getCommand("test").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player plr = (Player) sender;
		ArmorStand as = plr.getLocation().getWorld().spawn(plr.getLocation(), ArmorStand.class);
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setCustomModelData(123456);
		item.setItemMeta(itemMeta);
		as.getEquipment().setHelmet(item);
		plr.getInventory().addItem(item);
		as.setVisible(false);
		as.addPassenger(plr);
		return true;
	}
}
