package me.silentprogram.advancedboats.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.silentprogram.advancedboats.AdvancedBoats;
import me.silentprogram.advancedboats.api.managers.BoatManager;
import me.silentprogram.advancedboats.api.managers.EventManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;


public class Listeners {
	AdvancedBoats plugin;
	ProtocolManager manager;
	EventManager eventManager;
	
	public Listeners(AdvancedBoats plugin) {
		this.plugin = plugin;
		this.manager = plugin.manager;
		this.eventManager = new EventManager();
	}
	
	public void createListeners() {
		//Create looking direction packet listener
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.LOOK) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Entity entity = event.getPlayer().getVehicle();
				BoatManager boatManager = new BoatManager((AdvancedBoats) plugin, entity);
				
				if (!boatManager.isBoat()) return;
				eventManager.rotateEvent(event.getPlayer(), (ArmorStand) entity);
			}
		});
		//Input packet listener
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Entity entity = event.getPlayer().getVehicle();
				BoatManager boatManager = new BoatManager((AdvancedBoats) plugin, entity);
				
				if (!boatManager.isBoat()) return;
				eventManager.inputEvent(event.getPlayer(), event);
			}
		});
	}
}
