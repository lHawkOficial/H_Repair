package me.hrepair.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.hrepair.Core;

public class API {

	public Boolean canRepair(ItemStack item) {
		if (item == null || item.getType().isEdible() || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getType().getMaxDurability() <= 0) return false;
		return true;
	}
	
	public static API get() {
		return Core.getInstance().getApi();
	}
	
}
