package me.zsergio.aurastaff.manage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class customItem {
	
	private Player player;
	private ItemStack item;
	
	public customItem(Player player, Material material, String name, int amount, short data) {
		this.player = player;
		this.item = new ItemStack(material, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		item.setItemMeta(meta);
	}
	
	public void setItem(int slot) {
		player.getInventory().setItem(slot, item);
		player.updateInventory();
	}

}
