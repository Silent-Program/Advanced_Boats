package me.silentprogram.advancedboats.api.managers;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

public class EventManager {
	//Handle events here
	public void rotateEvent(Player plr, ArmorStand entity) {
		String[] name = entity.getCustomName().split("_");
		ArmorStand stand = (ArmorStand) entity;
		
		Vector entityVelocity = stand.getVelocity();
		Block block = plr.getLocation().getBlock();
		
		stand.setRotation(plr.getLocation().getYaw(), plr.getLocation().getPitch());
		stand.setVelocity(entityVelocity.setY((block.getType() == Material.WATER || block.getType() == Material.KELP || block.getType() == Material.SEAGRASS || block.getType() == Material.TALL_SEAGRASS || block.getType() == Material.KELP_PLANT ? 0.01 : entityVelocity.getY())));
	}
	
	public void inputEvent(Player plr, PacketEvent event){
		ArmorStand stand = (ArmorStand) plr.getVehicle();
		
		PacketContainer packet = event.getPacket();
		float sidewaysFloat = packet.getFloat().read(0);
		float forwardFloat = packet.getFloat().read(1);
		if (forwardFloat == 0 && sidewaysFloat == 0) return;
		Vector vector = generateVector(plr, forwardFloat, sidewaysFloat);
		
		stand.setVelocity(vector.multiply(0.5).setY(stand.getVelocity().getY()));
	}
	
	//Logic for input packet
	private Vector generateVector(Player p, float forwards, float sideways) {
		Vector rightVector = new Vector(-Math.cos(Math.toRadians(p.getLocation().getYaw())), 0, -Math.sin(Math.toRadians(p.getLocation().getYaw())));
		
		Vector velocity = p.getLocation().getDirection();
		velocity.setY(0);
		velocity.normalize();
		velocity.multiply(forwards);
		velocity.add(rightVector.multiply(-sideways));
		velocity.normalize();
		if (!NumberConversions.isFinite(velocity.getX())) velocity.setX(0);
		if (!NumberConversions.isFinite(velocity.getY())) velocity.setY(0);
		if (!NumberConversions.isFinite(velocity.getZ())) velocity.setZ(0);
		return velocity;
	}
}
