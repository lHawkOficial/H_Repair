package me.hrepair.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class API {

	public Boolean canRepair(ItemStack item) {
		if (item == null || item.getType().isEdible() || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getType().getMaxDurability() <= 0) return false;
		return true;
	}
	
}
