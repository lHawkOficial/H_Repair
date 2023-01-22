package me.hrepair.objects.managers;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;

@Getter
public class NotificationManager {

	private HashMap<Player, HashMap<Integer, List<ItemStack>>> map = new HashMap<>();
	
	public void checkPlayer(Player p) {
		PlayerRepair pr = PlayerRepair.check(p);
		
	}
	
	public static NotificationManager get() {
		return Core.getInstance().getNotificationmanager();
	}
	
}
