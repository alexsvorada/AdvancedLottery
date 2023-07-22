package com.gmail.ianlim224.advancedlottery.parser;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import org.bukkit.entity.Player;

public class PluginParser implements Parser {
    @Override
    public String parse(Player player, String msg) {
        return AdvancedLottery.f(msg);
    }
}
