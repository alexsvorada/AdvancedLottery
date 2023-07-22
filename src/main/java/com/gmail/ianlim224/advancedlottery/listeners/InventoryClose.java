package com.gmail.ianlim224.advancedlottery.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.gmail.ianlim224.advancedlottery.gui.ConfirmGUI;
import com.gmail.ianlim224.advancedlottery.gui.ConfirmHolder;

public class InventoryClose implements Listener {
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if(event.getView().getTopInventory().getHolder() instanceof ConfirmHolder) {
			ConfirmGUI.removePlayer((Player) event.getPlayer());
		}
	}
}
