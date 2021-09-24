package me.silentprogram.advancedboats.api.builders;

import me.silentprogram.advancedboats.api.enums.BoatType;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BoatBuilder {
	ArmorStand boat;
	Player plr;
	Location plrLoc;
	UUID uuid;
	BoatType boatType;
	
	//Add other constructors to pass to the next constructor when only using one parameter.
	public BoatBuilder(UUID uuid, Player plr, BoatType boatType) {
		plrLoc = plr.getLocation();
		this.plr = plr;
		this.uuid = uuid;
		this.boatType = boatType;
	}
	//Apply everything to armorstand, and spawn it
	public ArmorStand build(){
		boat = plrLoc.getWorld().spawn(plrLoc, ArmorStand.class);
		boat.setCustomName("ADVANCEDBOAT_" + uuid.toString() + "_" + plr.getName() + "_" + boatType.name());
		boat.setCustomNameVisible(false);
		boat.setVisible(false);
		return boat;
	}
	//Getters, and setters
	public void setBoatType(BoatType boatType) {
		this.boatType = boatType;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public BoatType getBoatType() {
		return boatType;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public Player getPlayer() {
		return plr;
	}
}
