package com.gmail.ianlim224.advancedlottery.parser;

import org.bukkit.entity.Player;

public interface Parser {
    String parse(Player player, String msg);
}
