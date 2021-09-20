package me.silentprogram.advancedboats.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.silentprogram.advancedboats.AdvancedBoats;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
				if (plr.getVehicle() == null) return;
				ArmorStand entity = (ArmorStand) plr.getVehicle();
				
				entity.setRotation(plr.getLocation().getYaw(), plr.getLocation().getPitch());
			}
		});
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player plr = event.getPlayer();
				Entity entity = plr.getVehicle();
				if (entity == null) return;
				PacketContainer packet = event.getPacket();
				float sidewaysFloat = packet.getFloat().read(0);
				float forwardFloat = packet.getFloat().read(1);
				boolean isJump = packet.getBooleans().read(0);
				if(forwardFloat == 0 && sidewaysFloat == 0 && !isJump) return;
				Vector vector = generateVector(plr, forwardFloat, sidewaysFloat);
				double yVel = entity.getVelocity().getY();
				//for boats check if the location below the player is water, if so set the Y to like 0.1 or smthng small
				entity.setVelocity(vector.multiply(0.5).setY((isJump ? (entity.isOnGround() ? 0.4 : yVel) : yVel)));
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
