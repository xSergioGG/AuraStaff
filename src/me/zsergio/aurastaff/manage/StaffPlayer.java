package me.zsergio.aurastaff.manage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffPlayer {
	
	private Player player;
	private boolean staff_enabled;
	private boolean vanish_enabled;
	private ItemStack[] inventory;
	
	public StaffPlayer(Player player) {
		this.player = player;
		staff_enabled = false;
		vanish_enabled = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean checkStaff() {
		if(staff_enabled == false) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkVanish() {
		if(vanish_enabled == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public void toggleVanish(boolean bool) {
		if(vanish_enabled == false) {
			player.sendMessage("§aVanish activado.");
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(!all.hasPermission("aura.staff")) {
					all.hidePlayer(player);
				}
			}
		
		} else {
			player.sendMessage("§cVanish desactivo.");
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.showPlayer(player);
			}
		}
		vanish_enabled = bool;
	}
	
	public void toggleStaff() {
		if(staff_enabled == true) {
			if(vanish_enabled == true) {
				toggleVanish(false);
			}
		}
		getInv();
	}
	
	public void getInv() {
		if(!staff_enabled == true) {
			//ENABLE
			toggleVanish(true);
			staff_enabled = true;
			player.sendMessage("§aStaff habilitado");
			inventory = player.getInventory().getContents();
			player.getInventory().clear();
			
			customItem item1 = new customItem(player, Material.COMPASS, "§dTeletransportador", 1, (short) 0);
			item1.setItem(0);
			
			customItem item2 = new customItem(player, Material.PACKED_ICE, "§dCongelar", 1, (short) 0);
			item2.setItem(4);
			
			customItem item3 = new customItem(player, Material.BLAZE_ROD, "§dVanish", 1, (short) 0);
			item3.setItem(8);
			
			player.updateInventory();
		} else {
			//DISABLE
			staff_enabled = false;
			player.sendMessage("§cStaff desactivado");
			player.getInventory().clear();
			player.getInventory().setContents(inventory);
			inventory = null;
			
			player.updateInventory();
		}
	}


}
