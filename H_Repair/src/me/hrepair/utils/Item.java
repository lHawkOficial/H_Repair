package me.hrepair.utils;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item extends me.hawkcore.utils.items.Item {

	private String permission;
	private boolean head = false;
	
	public Item(ItemStack item) {
		super(item);
	}

}
