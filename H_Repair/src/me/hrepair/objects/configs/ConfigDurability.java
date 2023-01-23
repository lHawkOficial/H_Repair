package me.hrepair.objects.configs;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class ConfigDurability {

	private double cost_default;
	private int autoFix;
	private HashMap<String, Double> items = new HashMap<>();
	private HashMap<Enchantment, Double> enchantments = new HashMap<>();
	
	public ConfigDurability() {
		FileConfiguration config = Core.getInstance().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Config.durability");
		autoFix = section.getInt("autoFix");
		for(String ID : section.getStringList("cost_item")) {
			try {
				items.put(ID.split(":")[0], Double.valueOf(ID.split(":")[1]));
			} catch (Exception e) {
				continue;
			}
		}
		for(String line : section.getStringList("cost_enchant")) {
			try {
				enchantments.put(Enchantment.getByName(line.split(":")[0].toUpperCase()), Double.valueOf(line.split(":")[1]));
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static ConfigDurability get() {
		return ConfigGeral.get().getConfigdurability();
	}
	
}
