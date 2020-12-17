package me.zsergio.aurastaff.listener;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.zsergio.aurastaff.AuraStaff;
import me.zsergio.aurastaff.manage.StaffManager;

public class Mechanics implements Listener {
	
	private AuraStaff plugin = AuraStaff.getInstance();
	private StaffManager manager = plugin.getStaffManager();
	
	public static ArrayList<UUID> frozen = new ArrayList<>();
	
	@EventHandler
	public void onCLickItem(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(!(player.getItemInHand() == null)) {
				if(player.getItemInHand().getItemMeta().getDisplayName() == "§dTeletransportador") {
					if(manager.isStaffEnabled(player) == true) {
						ArrayList<Player> list = new ArrayList<>();
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							list.add(all);
						}
						int ran = new Random().nextInt(Bukkit.getOnlinePlayers().size());
						player.teleport(list.get(ran));
						player.sendMessage("§dTeletransportando a §b"+list.get(ran).getDisplayName()+"§d.");
					}
				}
					
				} if(player.getItemInHand().getItemMeta().getDisplayName() == "§dVanish") {
					if(manager.isStaffEnabled(player) == true) {
						if(manager.getStaffPlayer(player).checkVanish() == true) {
							manager.getStaffPlayer(player).toggleVanish(false);
						} else {
							manager.getStaffPlayer(player).toggleVanish(true);
						}
						
					}
					
				}
		}
	}
	
	@EventHandler
	public void onClickPlayer(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		if(!(player.getItemInHand() == null)) {
			if(player.getItemInHand().getItemMeta().getDisplayName() == "§dCongelar") {
				if(event.getRightClicked() instanceof Player) {
					Player target = (Player) event.getRightClicked();
					player.performCommand("ss "+target.getDisplayName());
				}
			}
		}
	}
	
	@EventHandler
	public void onClickInv(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			try {
				Player player = (Player) event.getWhoClicked();
				if(player.getItemInHand().getItemMeta().getDisplayName() == "§dTeletransportador") {
					event.setCancelled(true);
				} if(player.getItemInHand().getItemMeta().getDisplayName() == "§dVanish") {
					event.setCancelled(true);
				}
				
				if(frozen.contains(player.getUniqueId())) {
					event.setCancelled(true);
				}
			} catch(NullPointerException e) {
				
			}
			
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if(frozen.contains(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(frozen.contains(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(manager.isStaffEnabled(player) == true) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(!all.hasPermission("aura.staff")) {
							all.hidePlayer(player);
						}
					}
				}
			}
		},5L);
	}

}
