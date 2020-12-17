package me.zsergio.aurastaff.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.zsergio.aurastaff.AuraStaff;
import me.zsergio.aurastaff.manage.StaffManager;

public class staffCMD implements CommandExecutor {

	private AuraStaff plugin = AuraStaff.getInstance();
	private StaffManager manager = plugin.getStaffManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("aura.staff")) {
			Player player = (Player) sender;
			manager.getStaffPlayer(player).toggleStaff();
		} else {
			sender.sendMessage("§cCreo que tú específicamente no eres staff y no puedes ejecutar este comando xDDD");
		}
		return true;
	}

}
