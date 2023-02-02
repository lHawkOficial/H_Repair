package me.hrepair.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hrepair.Core;
import me.hrepair.utils.API;
import me.hrepair.utils.Mensagens;

public class CommandRepairAll implements CommandExecutor {

	public CommandRepairAll() {
		Core.getInstance().getCommand("repairall").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String lb, String[] args) {
		if (!(s instanceof Player)) return false;
		Player p = (Player)s;
		p.playSound(p.getLocation(), Sound.NOTE_BASS, 0.5f, 10);
		if (!p.hasPermission("H_Reparar.FixAllCommand")) {
			p.sendMessage(Mensagens.get().getPermissionCommand());
			return false;
		}
		API.get().repairAll(p);
		return false;
	}
	
}
