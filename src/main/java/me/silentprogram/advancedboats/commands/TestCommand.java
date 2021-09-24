package me.silentprogram.advancedboats.commands;

import me.silentprogram.advancedboats.AdvancedBoats;
import me.silentprogram.advancedboats.api.builders.BoatBuilder;
import me.silentprogram.advancedboats.api.enums.BoatType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class TestCommand implements CommandExecutor {
	AdvancedBoats plugin;
	
	public TestCommand(AdvancedBoats plugin) {
		this.plugin = plugin;
		plugin.getCommand("test").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player plr = (Player) sender;
		/*
		ArmorStand as = plr.getLocation().getWorld().spawn(plr.getLocation(), ArmorStand.class);
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setCustomModelData(123456);
		item.setItemMeta(itemMeta);
		as.getEquipment().setHelmet(item);
		as.setCustomName("ADVANCEDVEHICLE_902191_" + plr.getName() + "_VEHICLETYPE");
		as.setCustomNameVisible(false);
		as.setVisible(false);
		as.addPassenger(plr);
		 */
		new BoatBuilder(UUID.randomUUID(), plr, BoatType.NORMAL).build().addPassenger(plr);
		return true;
	}
}
