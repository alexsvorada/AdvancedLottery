package com.gmail.ianlim224.advancedlottery.utils;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {
    private final Map<UUID, Long> cache;
    private final long cooldownDuration;

    public Cooldown(Duration duration) {
        this.cooldownDuration = duration.toMillis();
        this.cache = new HashMap<>();
    }

    public void addCooldown(Player player) {
        this.cache.put(player.getUniqueId(), System.currentTimeMillis() + cooldownDuration);
    }

    public Duration getTimeLeft(Player player) {
        return Duration.ofMillis(cache.get(player.getUniqueId()) - System.currentTimeMillis());
    }

    public boolean isReady(Player player) {
        if (!cache.containsKey(player.getUniqueId()))
            return true;
        return System.currentTimeMillis() > cache.get(player.getUniqueId());
    }
}
