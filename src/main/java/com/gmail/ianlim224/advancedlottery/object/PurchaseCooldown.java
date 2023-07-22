package com.gmail.ianlim224.advancedlottery.object;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.utils.Cooldown;

import java.time.Duration;

public class PurchaseCooldown {
    private static PurchaseCooldown instance;
    private final Cooldown cooldown;

    public PurchaseCooldown(Duration duration) {
        this.cooldown = new Cooldown(duration);
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public static PurchaseCooldown getInstance(AdvancedLottery plugin) {
        if(instance == null) {
            Duration duration = Duration.ofSeconds(plugin.getConfig().getInt("buy_tickets_cooldown_in_seconds"));
            instance = new PurchaseCooldown(duration);
        }

        return instance;
    }
}
