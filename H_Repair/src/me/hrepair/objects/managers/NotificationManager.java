package me.hrepair.objects.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.configs.ConfigNotifications;

@Getter
public class NotificationManager {

	private HashMap<Player, HashMap<Integer, List<ItemStack>>> map = new HashMap<>();
	
	public boolean isToNotificate(Player p, ItemStack item, int percent) {
		PlayerRepair pr = PlayerRepair.check(p);
		if (!pr.getNotifications()) return false;
		if (!map.containsKey(p)) map.put(p, new HashMap<>());
		HashMap<Integer, List<ItemStack>> map = this.map.get(p);
		if (map.isEmpty()) {
			for(int value : ConfigNotifications.get().getPercents()) {
				map.put(value, new ArrayList<>());
			}
		}
		for(int value : ConfigNotifications.get().getPercents()) {
			if (map.get(value).contains(item)) continue;
			if (percent > value) continue;
			map.get(value).add(item);
			return true;
		}
		return false;
	}
	
	public static NotificationManager get() {
		return Core.getInstance().getNotificationmanager();
	}
	
}
