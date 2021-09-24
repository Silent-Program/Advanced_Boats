package me.silentprogram.advancedboats.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.silentprogram.advancedboats.AdvancedBoats;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;


public class Listeners {
	AdvancedBoats plugin;
	ProtocolManager manager;
	
	public Listeners(AdvancedBoats plugin) {
		this.plugin = plugin;
		this.manager = plugin.manager;
	}
	
	public void createListeners() {
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.LOOK) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player plr = event.getPlayer();
				Entity entity = plr.getVehicle();
				if (entity == null || entity.getType() != EntityType.ARMOR_STAND) return;
				if(!entity.getCustomName().split("_")[0].equals("ADVANCEDVEHICLE")) return;
				ArmorStand stand = (ArmorStand) entity;
				
				Vector entityVelocity = stand.getVelocity();
				Block block = plr.getLocation().getBlock();
				
				stand.setRotation(plr.getLocation().getYaw(), plr.getLocation().getPitch());
				stand.setVelocity(entityVelocity.setY((block.getType() == Material.WATER || block.getType() == Material.KELP || block.getType() == Material.SEAGRASS|| block.getType() == Material.TALL_SEAGRASS|| block.getType() == Material.KELP_PLANT ? 0.01 : entityVelocity.getY())));
			}
		});
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player plr = event.getPlayer();
				Entity entity = plr.getVehicle();
				if (entity == null || entity.getType() != EntityType.ARMOR_STAND) return;
				if(!entity.getCustomName().split("_")[0].equals("ADVANCEDVEHICLE")) return;
				ArmorStand stand = (ArmorStand) entity;
				
				PacketContainer packet = event.getPacket();
				float sidewaysFloat = packet.getFloat().read(0);
				float forwardFloat = packet.getFloat().read(1);
				if (forwardFloat == 0 && sidewaysFloat == 0) return;
				Vector vector = generateVector(plr, forwardFloat, sidewaysFloat);
				//for boats check if the location below the player is water, if so set the Y to like 0.1 or smthng small
				stand.setVelocity(vector.multiply(0.5).setY(stand.getVelocity().getY()));
			}
		});
	}
	
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
