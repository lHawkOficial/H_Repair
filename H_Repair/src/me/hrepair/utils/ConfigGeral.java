package me.hrepair.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class ConfigGeral {

	private String statusAtivado,
	statusDesativado;
	
	public ConfigGeral() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Config");
		statusAtivado = section.getString("status.ativado").replace("&", "§");
		statusDesativado = section.getString("status.desativado").replace("&", "§");
	}
	
	public static ConfigGeral get() {
		return Core.getInstance().getConfiggeral();
	}
	
}
