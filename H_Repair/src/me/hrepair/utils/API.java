package me.hrepair.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.hawkcore.utils.Eco;
import me.hawkcore.utils.boosbar.BossBar;
import me.hrepair.Core;
import me.hrepair.objects.PlayerRepair;

public class API {

	public Boolean canRepair(ItemStack item) {
		if (item == null || item.getType().isEdible() || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getType().getMaxDurability() <= 0) return false;
		return true;
	}
	
	public static API get() {
		return Core.getInstance().getApi();
	}
	
	@SuppressWarnings("deprecation")
	public void repairAll(Player p) {
		PlayerRepair pr = PlayerRepair.check(p);
		List<ItemStack> items = pr.getItems();
		if (!items.isEmpty()) {
			double valor = pr.getValueRepair();
			if (Eco.get().has(p, valor)) {
				int total = pr.getTotalRepair();
				Eco.get().withdrawPlayer(p, valor);
				pr.repairAllItens();
				p.sendMessage(Mensagens.get().getFixedItemsChat().replace("{valor}", Eco.get().format(valor)).replace("{total}", String.valueOf(total)));
				p.sendTitle(Mensagens.get().getFixedItemsTitle().split("<nl>")[0], Mensagens.get().getFixedItemsTitle().split("<nl>")[1]);
				BossBar.send(p, Mensagens.get().getFixedItemsBar().replace("{total}", String.valueOf(total)).replace("{valor}", Eco.get().format(valor)));
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.5f, 10);
				p.updateInventory();
				return;
			} else {
				p.sendMessage(Mensagens.get().getNoMoney());
				p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
				return;
			}
		} else {
			p.sendMessage(Mensagens.get().getNoItemInventory());
			p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
			return;
		}
	}
	
}
