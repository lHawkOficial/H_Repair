package me.hrepair.utils;

import org.bukkit.entity.Player;

import me.hawkcore.utils.boosbar.BossBar;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.managers.Manager;

public class Timer implements Runnable {

	private Manager manager = Manager.get();
	
	@Override
	public void run() {
		
		for(PlayerRepair pr : manager.getPlayers()) {
			Player p = pr.getPlayer();
			if (p == null) continue;
			BossBar.send(p, "§6"+pr.getItemPercentHand() + "%");
		}
		
	}

}
