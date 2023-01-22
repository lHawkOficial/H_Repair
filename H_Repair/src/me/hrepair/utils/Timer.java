package me.hrepair.utils;

import org.bukkit.entity.Player;

import me.hawkcore.utils.API;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.managers.Manager;

public class Timer implements Runnable {

	private Manager manager = Manager.get();
	
	@Override
	public void run() {
		
		for(PlayerRepair pr : manager.getPlayers()) {
			Player p = pr.getPlayer();
			if (p == null) continue;
			pr.checkItemRepair();
			API.get().sendActionBarMessage(p, "§6"+pr.getItemPercentHand() + "%");
		}
		
	}

}
