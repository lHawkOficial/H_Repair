package me.hrepair.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;

public class PlayersListeners implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerRepair.check(p);
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.hasMetadata("playerrepair")) p.removeMetadata("playerrepair", Core.getInstance());
	}
	
}
