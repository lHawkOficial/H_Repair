package me.hrepair.objects.menus;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import me.hrepair.Core;
import me.hrepair.utils.Mensagens;

@Getter
public class MenuMain {

	private String name;
	private int row;
	
	public MenuMain() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("MenuMain");
		this.name = section.getString("name").replace("&", "§");
		this.row = section.getInt("row");
	}
	
	public void open(Player p) {
		try {
			
			Inventory inv = Bukkit.createInventory(p, row*9, name);
			
			p.openInventory(inv);
			p.updateInventory();
			p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 0.5f, 10);
			
		} catch (Exception e) {
			p.closeInventory();
			p.sendMessage(Mensagens.get().getErrorMenu());
			e.printStackTrace();
		}
	}
	
	public static MenuMain get() {
		return Core.getInstance().getMenumain();
	}
	
}
