package me.silentprogram.advancedboats.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.silentprogram.advancedboats.AdvancedBoats;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Random;

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
				if(plr.getVehicle() == null) return;
				ArmorStand entity = (ArmorStand) plr.getVehicle();
				
				Location locCopy = entity.getLocation().clone();
				locCopy.setPitch(plr.getLocation().getPitch());
				locCopy.setYaw(plr.getLocation().getYaw());
				
				entity.teleport(locCopy);
			}
		});
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player plr = event.getPlayer();
				Entity entity = plr.getVehicle();
				if(entity == null) return;
				PacketContainer packet = event.getPacket();
				float sidewaysFloat = packet.getFloat().read(0);
				float forwardFloat = packet.getFloat().read(1);
				
				entity.setVelocity(new Vector((sidewaysFloat == 0 ? 0 : (sidewaysFloat > 0 ? 0.10 : -0.10)), 0, (forwardFloat == 0 ? 0 : (forwardFloat > 0 ? 0.10 : -0.10))));
			}
		});
	}
	
}
