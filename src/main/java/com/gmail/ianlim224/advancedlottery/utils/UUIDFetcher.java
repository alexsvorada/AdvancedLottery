package com.gmail.ianlim224.advancedlottery.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UUIDFetcher implements Listener {
	private static Map<String, UUID> map = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		if(!map.containsKey(name)) {
			map.put(name, p.getUniqueId());
		}
	}
	
	public static void reload() {
		map = new HashMap<>();
		for(Player p: Bukkit.getOnlinePlayers()) {
			map.put(p.getName(), p.getUniqueId());
		}
	}
	
	public static UUID getPlayerUUID(String name) {
		return map.get(name);
	}

}
