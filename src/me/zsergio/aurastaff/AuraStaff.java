package me.zsergio.aurastaff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.zsergio.aurastaff.commands.ssCMD;
import me.zsergio.aurastaff.commands.staffCMD;
import me.zsergio.aurastaff.listener.Mechanics;
import me.zsergio.aurastaff.manage.StaffManager;

public class AuraStaff extends JavaPlugin implements Listener {
	
	private static AuraStaff instance;
	
	private StaffManager staffManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		staffManager = new StaffManager();
		
		getCommand("staff").setExecutor(new staffCMD());
		getCommand("ss").setExecutor(new ssCMD());
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(this, this);
		pm.registerEvents(new Mechanics(), this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		staffManager.checkPlayer(player);
	}
	
	public static AuraStaff getInstance() {
		return instance;
	}
	
	public StaffManager getStaffManager() {
		return staffManager;
	}

}
