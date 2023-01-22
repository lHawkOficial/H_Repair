package me.hrepair.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hrepair.Core;
import me.hrepair.objects.menus.MenuMain;
import me.hrepair.utils.Mensagens;

public class CommandRepair implements CommandExecutor {

	public CommandRepair() {
		Core.getInstance().getCommand("reparar").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String lb, String[] args) {
		if (!(s instanceof Player)) return false;
		Player p = (Player)s;
		p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 0.5f, 10);
		//PlayerRepair pr = PlayerRepair.check(p);
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (p.hasPermission("H_Reparar.Adm")) {
					Core.getInstance().reloadPlugin();
					p.sendMessage(Mensagens.get().getReloadCommand());
					return false;
				}
			}
		}
		MenuMain.get().open(p);
		return false;
	}
	
}
