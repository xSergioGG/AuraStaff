package me.zsergio.aurastaff.manage;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.zsergio.aurastaff.AuraStaff;
import me.zsergio.aurastaff.listener.Mechanics;

public class StaffManager {
	
	private HashMap<UUID, StaffPlayer> players = new HashMap<>();
	
	public StaffManager() {
		startSSCheck();
	}
	
	public boolean isStaffEnabled(Player player) {
		if(players.containsKey(player.getUniqueId())) {
			if(players.get(player.getUniqueId()).checkStaff() == true) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void checkPlayer(Player player) {
		if(player.hasPermission("aura.staff")) {
			players.put(player.getUniqueId(), new StaffPlayer(player));
		} else {
			return;
		}
	}
	
	public StaffPlayer getStaffPlayer(Player player) {
		if(players.containsKey(player.getUniqueId())) {
			return players.get(player.getUniqueId());
		} else {
			return null;
		}
	}
	
	private void startSSCheck() {
		
		int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(AuraStaff.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(UUID uuids : Mechanics.frozen) {
					try {
						Player player = Bukkit.getPlayer(uuids);
						player.sendMessage("§cActualmente estás en revisión.");
						player.sendMessage("§cSigue las instrucciones del Staff para no ser sancionado.");
						player.playSound(player.getLocation(), Sound.ANVIL_LAND, 100, 100);
					} catch(NullPointerException e) {
						return;
					}
				}
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(!all.hasPermission("aura.staff") ) {
						for(StaffPlayer staffs : players.values()) {
								if(staffs.checkVanish() == true) {
									all.hidePlayer(staffs.getPlayer());
								}
							}
					}
				}
				
			}
		}, 0L, 20L);
	}
}
