package me.silentprogram.advancedboats.api.managers;

import me.silentprogram.advancedboats.AdvancedBoats;
import me.silentprogram.advancedboats.api.enums.BoatType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BoatManager {
	AdvancedBoats plugin;
	BoatType type;
	ArmorStand boat;
	String[] name;
	String ownerName;
	UUID uuid;
	Map<String, BoatType> map = new HashMap<>();
	
	public BoatManager(AdvancedBoats plugin, Entity entity) {
		if(!(entity instanceof ArmorStand)) return;
		if(entity == null) return;
		if(entity.getCustomName() == null) return;
		if (!entity.getCustomName().contains("ADVANCEDBOAT")) return;
		this.boat = (ArmorStand) entity;
		this.plugin = plugin;
		this.name = boat.getCustomName().split("_");
		this.uuid = UUID.fromString(name[1]);
		this.ownerName = name[2];
		//Add all enums to the map
		for (BoatType i : BoatType.values()) {
			map.put(i.name(), i);
		}
		
		//Change the type variable to the type gotten from the typemap
		this.type = map.get(name[3]);
	}
	
	//Gets the type of the boat
	public BoatType getBoatType() {
		return type;
	}
	public boolean isBoat(){
		return boat != null;
	}
	
	public UUID getUUID(){
		return uuid;
	}
}
