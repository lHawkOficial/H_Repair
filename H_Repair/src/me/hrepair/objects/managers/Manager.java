package me.hrepair.objects.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;

@Getter
public class Manager {

	private List<PlayerRepair> players = new ArrayList<>();
	
	public PlayerRepair getPlayerRepair(String name) {
		Iterator<PlayerRepair> it = players.iterator();
		while(it.hasNext()) {
			PlayerRepair pr = it.next();
			if (pr.getName().equalsIgnoreCase(name)) return pr;
		}
		return null;
	}
	
	public static Manager get() {
		return Core.getInstance().getManager();
	}
	
}
