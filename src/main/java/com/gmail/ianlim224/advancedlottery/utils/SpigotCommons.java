package com.gmail.ianlim224.advancedlottery.utils;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class SpigotCommons {
    private static final long MILLION = 1000000L;
    private static final long BILLION = 1000000000L;
    private static final long TRILLION = 1000000000000L;
    private static DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###,##0.00");
    private SpigotCommons() {
    }

    public static void setMoneyFormat(String format, AdvancedLottery plugin) {
        try {
            MONEY_FORMAT = new DecimalFormat(format);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().info("Invalid money_display_format! Resetting to default format...");
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static String formatMoney(double money) {
        return money < MILLION ? MONEY_FORMAT.format(money) :
                money < BILLION ? MONEY_FORMAT.format(money / MILLION) + " million" :
                        money < TRILLION ? MONEY_FORMAT.format(money / BILLION) + " billion" :
                                MONEY_FORMAT.format(money / TRILLION) + " trillion";
    }

    public static String f(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
