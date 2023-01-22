package me.hrepair.utils;

import lombok.Getter;
import me.hrepair.Core;

@Getter
public class Mensagens {

	private String ReloadCommand,
	ErrorMenu,
	PermissionCommand,
	PermissionError,
	CommandWorld,
	noItemHand,
	noItemInventory,
	noMoney,
	fixedItemChat,
	fixedItemTitle,
	fixedItemBar,
	fixedItemsChat,
	fixedItemsTitle,
	fixedItemsBar,
	notification,
	notificationTitle,
	fixedAutoChat,
	fixedAutoTitle,
	fixedAutoBar,
	notificationBar;
	
	public Mensagens() {
		notificationBar = replace("notificationBar");
		fixedAutoBar = replace("fixedAutoBar");
		fixedAutoTitle = replace("fixedAutoTitle");
		fixedAutoChat = replace("fixedAutoChat");
		notificationTitle = replace("notificationTitle");
		notification = replace("notification");
		fixedItemsBar = replace("fixedItemsBar");
		fixedItemsTitle = replace("fixedItemsTitle");
		fixedItemsChat = replace("fixedItemsChat");
		fixedItemBar = replace("fixedItemBar");
		fixedItemTitle = replace("fixedItemTitle");
		fixedItemChat = replace("fixedItemChat");
		noMoney = replace("noMoney");
		noItemInventory = replace("noItemInventory");
		noItemHand = replace("noItemHand");
		CommandWorld = replace("CommandWorld");
		PermissionError = replace("PermissionError");
		PermissionCommand = replace("PermissionCommand");
		ErrorMenu = replace("ErrorMenu");
		ReloadCommand = replace("ReloadCommand");
	}
	
	private String replace(String msg) {return Core.getInstance().getConfig().getString("Mensagens." + msg).replace("{tag}", Core.getInstance().getTag()).replace("&", "§");}
	
	public static Mensagens get() {
		return Core.getInstance().getMensagens();
	}
	
}
