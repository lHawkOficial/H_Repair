package me.hrepair.objects.configs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class ConfigNotifications {

	private List<Integer> percents = new ArrayList<>();
	
	public ConfigNotifications() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Config.notifications");
		for(String line : section.getStringList("percents")) {
			try {
				percents.add(Integer.valueOf(line));
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static ConfigNotifications get() {
		return ConfigGeral.get().getConfignotifications();
	}
	
}
