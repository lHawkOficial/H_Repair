package me.hrepair.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.hawkcore.tasks.Task;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.menus.MenuMain;

public class MenuListeners implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equals(MenuMain.get().getName())) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			p.playSound(p.getLocation(), Sound.CLICK, 0.5f, 10);
			PlayerRepair pr = PlayerRepair.check(p);
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(MenuMain.get().getIconFixAuto().getDisplayName())) {
					if (pr.getAutoRepair()) pr.setAutoRepair(false);
					else pr.setAutoRepair(true);
					Task.runAsync(()->pr.save());
					MenuMain.get().open(p);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(MenuMain.get().getIconNotifications().getDisplayName())) {
					if (pr.getNotifications()) pr.setNotifications(false);
					else pr.setNotifications(true);
					Task.runAsync(()->pr.save());
					MenuMain.get().open(p);
					return;
				}
			}
			
		}
	}
	
}
