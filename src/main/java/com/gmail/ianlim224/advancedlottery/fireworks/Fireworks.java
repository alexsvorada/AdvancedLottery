package com.gmail.ianlim224.advancedlottery.fireworks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class Fireworks {
	public void shootFireworks(Player player, AdvancedLottery plugin) {
		if(!(player.isOnline())) {
			return;
		}
		Location loc = player.getLocation();
		
		FireworkEffect effect = FireworkEffect.builder().flicker(false).trail(true).withColor(Color.RED).withFade(Color.ORANGE).with(FireworkEffect.Type.STAR).build();
		final Firework firework = player.getWorld().spawn(loc, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(1);
		meta.addEffect(effect);
		firework.setFireworkMeta(meta);
	}
}
