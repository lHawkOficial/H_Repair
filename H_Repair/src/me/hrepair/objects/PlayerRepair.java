package me.hrepair.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import lombok.Getter;
import lombok.Setter;
import me.hawkcore.utils.Save;
import me.hrepair.Core;
import me.hrepair.objects.managers.Manager;

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
