package me.zsergio.aurastaff.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.zsergio.aurastaff.listener.Mechanics;

public class ssCMD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("aura.staff")) {
			Player player = (Player) sender;
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if(!Mechanics.frozen.contains(target.getUniqueId())) {
					player.sendMessage("§dJugador §b"+target.getDisplayName()+" §dcongelado correctamente.");
					Mechanics.frozen.add(target.getUniqueId());
				} else {
					player.sendMessage("§dJugador §b"+target.getDisplayName()+" §ddescongelado correctamente.");
					Mechanics.frozen.remove(target.getUniqueId());
				}
			} else {
				sender.sendMessage("§cUso correcto: /ss <jugador>");
			}
		} else {
			sender.sendMessage("§cCreo que tú específicamente no eres staff y no puedes ejecutar este comando xDDD");
		}
		return true;
	}

}
