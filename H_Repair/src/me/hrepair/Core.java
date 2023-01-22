package me.hrepair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.hawkcore.tasks.Task;
import me.hrepair.commands.CommandRepair;
import me.hrepair.listeners.PlayersListeners;
import me.hrepair.objects.PlayerRepair;
import me.hrepair.objects.managers.Manager;
import me.hrepair.objects.menus.MenuMain;
import me.hrepair.utils.API;
import me.hrepair.utils.Mensagens;
import net.milkbowl.vault.economy.Economy;

@Getter
public class Core extends JavaPlugin {
	
	@Getter
	private static Core instance;
	private String tag,version = "§d"+getDescription().getVersion();
	private Economy eco;
	private Manager manager;
	private API api;
	private Mensagens mensagens;
	private MenuMain menumain;
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		reloadPlugin();
		Task.run(()-> eco = me.hawkcore.Core.getInstance().getEcon()); 
		
		new CommandRepair();
		List<Listener> events = new ArrayList<>();
		events.add(new PlayersListeners());
		events.forEach(event -> Bukkit.getPluginManager().registerEvents(event, this));
		
		sendConsole(" ");
		sendConsole(tag + " &aH_Repair iniciado com sucesso! &6[Author lHawk_] " + version);
		sendConsole(" ");
	}
	
	@Override
	public void onDisable() {
		sendConsole(" ");
		sendConsole(tag + " &cH_Repair desligado com sucesso! &6[Author lHawk_] " + version);
		sendConsole(" ");
	}
	
	public void reloadPlugin() {
		reloadConfig();
		tag = getConfig().getString("Config.tag").replace("&", "§");
		api = new API();
		manager = new Manager();
		mensagens = new Mensagens();
		menumain = new MenuMain();
		File folder = new File(getDataFolder() + "/players");
		if (!folder.exists()) folder.mkdir();
		for(File file : folder.listFiles()) {
			if (!file.getName().endsWith(".json")) continue;
			new PlayerRepair(file.getName().replace(".json", new String()));
		}
		for(Player all : Bukkit.getOnlinePlayers()) {
			if (all.hasMetadata("playerrepair")) all.removeMetadata("playerrepair", this);
			PlayerRepair.check(all);
		}
	}
	
	public void sendConsole(String msg) {Bukkit.getConsoleSender().sendMessage(msg.replace("&", "§"));}

}
