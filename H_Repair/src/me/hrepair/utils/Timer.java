package me.hrepair.utils;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.hawkcore.utils.Eco;
import me.hawkcore.utils.boosbar.BossBar;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.configs.ConfigDurability;
import me.hrepair.objects.managers.Manager;

public class Timer implements Runnable {

	private Manager manager = Manager.get();
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		for(PlayerRepair pr : manager.getPlayers()) {
			Player p = pr.getPlayer();
			if (p == null) continue;
			pr.checkItemRepair();
			if (!pr.getAutoRepair()) continue;
			int percent = ConfigDurability.get().getAutoFix();
			List<ItemStack> items = pr.getItems(percent);
			if (items.isEmpty()) continue;
			double valor = pr.getValueRepair(percent);
			if (!Eco.get().has(p, valor)) continue;
			int total = pr.getTotalRepair(percent);
			Eco.get().withdrawPlayer(p, valor);
			pr.repairAllItens(percent);
			p.sendMessage(Mensagens.get().getFixedAutoChat().replace("{valor}", Eco.get().format(valor)).replace("{total}", String.valueOf(total)));
			p.sendTitle(Mensagens.get().getFixedAutoTitle().split("<nl>")[0], Mensagens.get().getFixedAutoTitle().split("<nl>")[1]);
			BossBar.send(p, Mensagens.get().getFixedAutoBar().replace("{total}", String.valueOf(total)).replace("{valor}", Eco.get().format(valor)));
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.5f, 10);
			p.updateInventory();
		}
		
	}

}
