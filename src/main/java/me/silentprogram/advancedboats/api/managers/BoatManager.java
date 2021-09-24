package me.silentprogram.advancedboats.api.managers;

import me.silentprogram.advancedboats.AdvancedBoats;
import me.silentprogram.advancedboats.api.enums.BoatType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class BoatManager {
	AdvancedBoats plugin;
	BoatType type;
	ArmorStand boat;
	String[] name;
	Map<String, BoatType> map = new HashMap<>();
	
	public BoatManager(AdvancedBoats plugin, ArmorStand boat) {
		this.plugin = plugin;
		this.boat = boat;
		this.name = boat.getCustomName().split("_");
		//Add all enums to the map
		for (BoatType i : BoatType.values()) {
			map.put(i.name(), i);
		}
		//Check if the boat is valid
		if (!boat.getCustomName().contains("ADVANCEDBOAT")) {
			this.boat = null;
			return;
		}
		//Change the type variable to the type gotten from the typemap
		this.type = map.get(name[2]);
	}
	
	//Gets the type of the boat
	public BoatType getBoatType() {
		return type;
	}

	
}
