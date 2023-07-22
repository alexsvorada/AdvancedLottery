package com.gmail.ianlim224.advancedlottery.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.ianlim224.advancedlottery.gui.ConfirmGUI;
import com.gmail.ianlim224.advancedlottery.gui.ConfirmHolder;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof ConfirmHolder) {
			ConfirmGUI.removePlayer(event.getPlayer());
		}
	}
}
