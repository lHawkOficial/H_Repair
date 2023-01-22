package me.hrepair.objects.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import me.hawkcore.utils.API;
import me.hawkcore.utils.Glass;
import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.configs.ConfigGeral;
import me.hrepair.utils.Item;
import me.hrepair.utils.Mensagens;

@Getter
public class MenuMain {

	private String name;
	private int row;
	private Item iconFixItem,
	iconFixAll,
	iconFixAuto,
	iconNotifications;
	private Glass glass;
	
	public MenuMain() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("MenuMain");
		this.name = section.getString("name").replace("&", "§");
		this.row = section.getInt("row");
		
		glass = new Glass(section.getStringList("Glass"), row);
		
		List<String> lore = new ArrayList<>();
		section = config.getConfigurationSection("MenuMain.Icons.iconFixItem");
		Item item = new Item(API.get().getItemStack(section.getString("ID")));
		item.setDisplayName(section.getString("name").replace("&", "§"));
		item.setSlot(section.getInt("slot")-1);
		if (section.getBoolean("glow")) item.setGlow();
		item.setPermission(section.getString("permission"));
		lore = new ArrayList<>(section.getStringList("lore"));
		lore.replaceAll(l -> l.replace("&", "§"));
		item.setLore(lore);
		this.iconFixItem = item;
		
		lore = new ArrayList<>();
		section = config.getConfigurationSection("MenuMain.Icons.iconFixAll");
		item = new Item(API.get().getItemStack(section.getString("ID")));
		item.setDisplayName(section.getString("name").replace("&", "§"));
		item.setSlot(section.getInt("slot")-1);
		if (section.getBoolean("glow")) item.setGlow();
		item.setPermission(section.getString("permission"));
		lore = new ArrayList<>(section.getStringList("lore"));
		lore.replaceAll(l -> l.replace("&", "§"));
		item.setLore(lore);
		this.iconFixAll = item;
		
		lore = new ArrayList<>();
		section = config.getConfigurationSection("MenuMain.Icons.iconFixAuto");
		item = new Item(API.get().getItemStack(section.getString("ID")));
		item.setDisplayName(section.getString("name").replace("&", "§"));
		item.setSlot(section.getInt("slot")-1);
		if (section.getBoolean("glow")) item.setGlow();
		item.setPermission(section.getString("permission"));
		lore = new ArrayList<>(section.getStringList("lore"));
		lore.replaceAll(l -> l.replace("&", "§"));
		item.setLore(lore);
		this.iconFixAuto = item;
		
		lore = new ArrayList<>();
		section = config.getConfigurationSection("MenuMain.Icons.iconNotifications");
		item = new Item(API.get().getItemStack(section.getString("ID")));
		item.setDisplayName(section.getString("name").replace("&", "§"));
		item.setSlot(section.getInt("slot")-1);
		if (section.getBoolean("glow")) item.setGlow();
		item.setPermission(section.getString("permission"));
		lore = new ArrayList<>(section.getStringList("lore"));
		lore.replaceAll(l -> l.replace("&", "§"));
		item.setLore(lore);
		this.iconNotifications = item;
		
	}
	
	public void open(Player p) {
		try {
			
			Inventory inv = Bukkit.createInventory(p, row*9, name);
			PlayerRepair pr = PlayerRepair.check(p);
			
			for(me.hawkcore.utils.items.Item item : glass.getVidros()) {
				inv.setItem(item.getSlot(), item.build().clone());
			}
			
			Item item = new Item(iconFixItem.build().clone());
			List<String> lore = new ArrayList<>(item.getLore());
			lore.replaceAll(l -> l.replace("{valor}", Core.getInstance().getEcon().format(0)));
			item.setLore(lore);
			inv.setItem(iconFixItem.getSlot(), item.build().clone());
			
			item = new Item(iconFixAll.build().clone());
			lore = new ArrayList<>(item.getLore());
			lore.replaceAll(l -> l.replace("{valor}", Core.getInstance().getEcon().format(0)));
			item.setLore(lore);
			inv.setItem(iconFixAll.getSlot(), item.build().clone());
			
			item = new Item(iconFixAuto.build().clone());
			lore = new ArrayList<>(item.getLore());
			lore.replaceAll(l -> l.replace("{status}", pr.getAutoRepair() ? ConfigGeral.get().getStatusAtivado() : ConfigGeral.get().getStatusDesativado()));
			item.setLore(lore);
			inv.setItem(iconFixAuto.getSlot(), item.build().clone());
			
			item = new Item(iconNotifications.build().clone());
			lore = new ArrayList<>(item.getLore());
			lore.replaceAll(l -> l.replace("{status}", pr.getNotifications() ? ConfigGeral.get().getStatusAtivado() : ConfigGeral.get().getStatusDesativado()));
			item.setLore(lore);
			inv.setItem(iconNotifications.getSlot(), item.build().clone());
			
			p.openInventory(inv);
			p.updateInventory();
			p.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 10);
			
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
