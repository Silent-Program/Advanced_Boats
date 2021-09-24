package me.silentprogram.advancedboats;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.silentprogram.advancedboats.api.managers.BoatManager;
import me.silentprogram.advancedboats.commands.TestCommand;
import me.silentprogram.advancedboats.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedBoats extends JavaPlugin {
	public ProtocolManager manager;
	BoatManager vehicleManager;
	Listeners listeners;
	
	
	@Override
	public void onEnable() {
		manager = ProtocolLibrary.getProtocolManager();
		registerEnums();
		new TestCommand(this);
		listeners = new Listeners(this);
		listeners.createListeners();
	}
	
	@Override
	public void onDisable() {
	
	}
	
	public BoatManager getVehicleManager() {
		return vehicleManager;
	}
	
	private void registerEnums() {
	
	}
}
