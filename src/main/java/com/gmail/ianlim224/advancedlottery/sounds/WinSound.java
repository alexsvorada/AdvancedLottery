package com.gmail.ianlim224.advancedlottery.sounds;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class WinSound implements ISound {
	public void playSound(Player player, AdvancedLottery plugin) {
		if (plugin.getConfig().getConfigurationSection("sounds").getBoolean("player_win_sound")) {
			try {
				player.playSound(player.getLocation(), Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 1, 0);
			} catch (IllegalArgumentException e) {
				player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 1, 0);
			}
		}
	}
}
