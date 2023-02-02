package me.hrepair.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.hawkcore.tasks.Task;
import me.hawkcore.utils.Eco;
import me.hawkcore.utils.boosbar.BossBar;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.menus.MenuMain;
import me.hrepair.utils.API;
import me.hrepair.utils.Mensagens;

public class MenuListeners implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		MenuMain menu = MenuMain.get();
		if (e.getView().getTitle().equals(menu.getName())) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			p.playSound(p.getLocation(), Sound.CLICK, 0.5f, 10);
			PlayerRepair pr = PlayerRepair.check(p);
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(menu.getIconFixAuto().getDisplayName())) {
					if (!p.hasPermission(menu.getIconFixAuto().getPermission())){
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						p.sendMessage(Mensagens.get().getPermissionError());
						return;
					}
					if (pr.getAutoRepair()) pr.setAutoRepair(false);
					else pr.setAutoRepair(true);
					Task.runAsync(()->pr.save());
					MenuMain.get().open(p);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(menu.getIconNotifications().getDisplayName())) {
					if (!p.hasPermission(menu.getIconNotifications().getPermission())){
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						p.sendMessage(Mensagens.get().getPermissionError());
						return;
					}
					if (pr.getNotifications()) pr.setNotifications(false);
					else pr.setNotifications(true);
					Task.runAsync(()->pr.save());
					MenuMain.get().open(p);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(menu.getIconFixItem().getDisplayName())) {
					p.closeInventory();
					if (!p.hasPermission(menu.getIconFixItem().getPermission())){
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						p.sendMessage(Mensagens.get().getPermissionError());
						return;
					}
					ItemStack item = p.getItemInHand();
					if (item != null && item.getType() != Material.AIR) {
						if (API.get().canRepair(item)) {
							double valor = pr.getValueItem(item);
							if (Eco.get().has(p, valor)) {
								Eco.get().withdrawPlayer(p, valor);
								pr.repairItem(item);
								p.sendMessage(Mensagens.get().getFixedItemChat().replace("{valor}", Eco.get().format(valor)));
								p.sendTitle(Mensagens.get().getFixedItemTitle().split("<nl>")[0], Mensagens.get().getFixedItemTitle().split("<nl>")[1]);
								BossBar.send(p, Mensagens.get().getFixedItemBar());
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.5f, 10);
								p.updateInventory();
								return;
							}else {
								p.sendMessage(Mensagens.get().getNoMoney());
								p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
								return;
							}
						}else {
							p.sendMessage(Mensagens.get().getItemError());
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
							return;
						}
					} else {
						p.sendMessage(Mensagens.get().getNoItemHand());
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						return;
					}
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(menu.getIconFixAll().getDisplayName())) {
					p.closeInventory();
					if (!p.hasPermission(menu.getIconFixAll().getPermission())){
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						p.sendMessage(Mensagens.get().getPermissionError());
						return;
					}
					API.get().repairAll(p);
					return;
				}
			}
			
		}
	}
	
}
