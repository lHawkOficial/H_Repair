package me.hrepair.objects;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import lombok.Getter;
import lombok.Setter;
import me.hawkcore.utils.Save;
import me.hawkcore.utils.boosbar.BossBar;
import me.hrepair.Core;
import me.hrepair.objects.configs.ConfigDurability;
import me.hrepair.objects.configs.ConfigNotifications;
import me.hrepair.objects.managers.Manager;
import me.hrepair.objects.managers.NotificationManager;
import me.hrepair.utils.API;
import me.hrepair.utils.Mensagens;

@Getter
@Setter
public class PlayerRepair {

	private String name;
	private Boolean autoRepair = false, notifications = false;
	private File file;
	
	public PlayerRepair(String name) {
		this.name = name;
		if (!new File(Core.getInstance().getDataFolder() + "/players").exists()) new File(Core.getInstance().getDataFolder()+"/players").mkdir();
		this.file = new File(Core.getInstance().getDataFolder() + "/players/"+name+".json");
		if (!file.exists()) {
			try {
				file.createNewFile();
				save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		load();
		Manager.get().getPlayers().add(this);
	}
	
	public static PlayerRepair check(Player p) {
		if (p.hasMetadata("playerrepair")) return (PlayerRepair) p.getMetadata("playerrepair").get(0).value();
		PlayerRepair pr = Manager.get().getPlayerRepair(p.getName());
		if (pr == null) pr = new PlayerRepair(p.getName());
		p.setMetadata("playerrepair", new FixedMetadataValue(Core.getInstance(), pr));
		return pr;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayerExact(name);
	}
	
	public HashMap<ItemStack, Integer> getItemsPercents() {
		HashMap<ItemStack, Integer> map = new HashMap<>();
		List<ItemStack> items = getItems();
		for(ItemStack item : items) {
			int total = item.getType().getMaxDurability();
			int value = item.getDurability();
			int percent = value * 100 / total;
			map.put(item, 100-percent);
		}
		return map;
	}
	
	public int getItemPercentHand() {
		Player p = getPlayer();
		if (p == null) return -1;
		ItemStack item = p.getItemInHand();
		if (item == null || item.getType() == Material.AIR) return 100;
		if (!API.get().canRepair(item)) return 100;
		return 100 - (item.getDurability() * 100 / item.getType().getMaxDurability());
	}
			
	public List<ItemStack> getItems() {
		Player p = getPlayer();
		if (p == null) return new ArrayList<>();
		List<ItemStack> items = new ArrayList<>();
		Inventory inv = p.getInventory();
		for(ItemStack item : inv.getContents()) {
			if (!API.get().canRepair(item)) continue;
			items.add(item);
		}
		return items;
	}
	
	public double getValueRepair() {
		double valor = 0;
		List<ItemStack> items = getItems();
		for(ItemStack item : items) valor+=getValueItem(item);
		return valor;
	}
	
	@SuppressWarnings("deprecation")
	public double getValueItem(ItemStack item) {
		if (item == null || item.getType() == Material.AIR || !API.get().canRepair(item)) return 0;
		double valor = item.getDurability()*(ConfigDurability.get().getItems().containsKey(item.getTypeId() + ">"+item.getData().getData()) ? ConfigDurability.get().getItems().get(item.getTypeId() + ">"+item.getData().getData()) : ConfigDurability.get().getCost_default());
		for(Enchantment enchant : ConfigDurability.get().getEnchantments().keySet()) {
			if (!item.getEnchantments().containsKey(enchant)) continue;
			valor+=ConfigDurability.get().getEnchantments().get(enchant);
		}
		return valor*item.getAmount();
	}
	
	public void repairItem(ItemStack item) {
		Player p = getPlayer();
		if (p == null) return;
		item.setDurability((short)0);
		p.updateInventory();
	}
	
	public void repairAllItens() {
		Player p = getPlayer();
		if (p == null) return;
		List<ItemStack> items = getItems();
		items.forEach(item -> item.setDurability((short)0));
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public void checkItemRepair() {
		Player p = getPlayer();
		if (p == null) return;
		HashMap<ItemStack, Integer> map = getItemsPercents();
		for(ItemStack item : map.keySet()) {
			int percent = map.get(item);
			for(int value : ConfigNotifications.get().getPercents()) {
				if (percent <= value && NotificationManager.get().isToNotificate(p, item, percent)) {
					p.sendMessage(Mensagens.get().getNotification().replace("{durabilidade}", percent+"%"));
					p.sendTitle(Mensagens.get().getNotificationTitle().split("<nl>")[0], Mensagens.get().getNotificationTitle().split("<nl>")[1]);
					BossBar.send(p, Mensagens.get().getNotificationBar());
					p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 0.5f, 0.5f);
					return;
				}
			}
		}
	}
	
	public void save() {
		List<Object> lista = new ArrayList<>();
		lista.add(autoRepair.toString());
		lista.add(notifications.toString());
		new Save(file, lista);
	}
	
	public void load() {
		List<Object> lista = Save.load(file);
		if (lista == null) return;
		if (lista.isEmpty()) return;
		this.autoRepair = Boolean.valueOf((String)lista.get(0));
		this.notifications = Boolean.valueOf((String)lista.get(1));
	}
	
}
