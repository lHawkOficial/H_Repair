package me.hrepair.utils;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class Mensagens {

	private String ReloadCommand,
	ErrorMenu;
	
	public Mensagens() {
		ErrorMenu = replace("ErrorMenu");
		ReloadCommand = replace("ReloadCommand");
	}
	
	private String replace(String msg) {return Core.getInstance().getConfig().getString("Mensagens." + msg).replace("{tag}", Core.getInstance().getTag()).replace("&", "§");}
	
	public static Mensagens get() {
		return Core.getInstance().getMensagens();
	}
	
}
