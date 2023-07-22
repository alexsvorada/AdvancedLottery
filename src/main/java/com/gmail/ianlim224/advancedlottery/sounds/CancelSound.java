package com.gmail.ianlim224.advancedlottery.sounds;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class CancelSound implements ISound {

	@Override
	public void playSound(Player player, AdvancedLottery plugin) {
		if (plugin.getConfig().getConfigurationSection("sounds").getBoolean("anvil_cancel_sound")) {
			try {
				player.playSound(player.getLocation(), Sound.valueOf("BLOCK_ANVIL_PLACE"), 1, 0);
			} catch (IllegalArgumentException e) {
				player.playSound(player.getLocation(), Sound.valueOf("ANVIL_LAND"), 1, -12);
			}
		}
	}
}
