package com.gmail.ianlim224.advancedlottery.parser;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderParser implements Parser {
    @Override
    public String parse(Player player, String msg) {
        return PlaceholderAPI.setPlaceholders(player, AdvancedLottery.f(msg));
    }
}
