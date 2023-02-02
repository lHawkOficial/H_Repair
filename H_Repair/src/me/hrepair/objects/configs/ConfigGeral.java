package me.hrepair.objects.configs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class ConfigGeral {

	private List<World> worlds = new ArrayList<>();
	private String statusAtivado,
	statusDesativado;
	private ConfigDurability configdurability;
	private ConfigNotifications confignotifications;
	private Boolean includeArmor;
	
	public ConfigGeral() {
		configdurability = new ConfigDurability();
		confignotifications = new ConfigNotifications();
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Config");
		includeArmor = section.getBoolean("includeArmor");
		statusAtivado = section.getString("status.ativado").replace("&", "§");
		statusDesativado = section.getString("status.desativado").replace("&", "§");
		for(String worldName : section.getStringList("worlds")) {
			try {
				worlds.add(Bukkit.getWorld(worldName));
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static ConfigGeral get() {
		return Core.getInstance().getConfiggeral();
	}
	
}
