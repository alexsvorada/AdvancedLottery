package com.gmail.ianlim224.advancedlottery.listeners;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.mysql.LotterySql;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private final LotterySql sql;

    public PlayerJoin(AdvancedLottery plugin) {
        this.sql = LotterySql.getInstance(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        sql.addPlayerIfNotExists(event.getPlayer());
    }
}
