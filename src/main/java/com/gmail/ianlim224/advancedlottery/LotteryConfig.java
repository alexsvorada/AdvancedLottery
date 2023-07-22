package com.gmail.ianlim224.advancedlottery;

import org.bukkit.configuration.file.FileConfiguration;

public class LotteryConfig {
    private AdvancedLottery plugin;

    public LotteryConfig(AdvancedLottery plugin) {
        this.plugin = plugin;
    }

    public void loadDefault() {
        FileConfiguration config = this.plugin.getConfig();

        config.options().header("Lottery version: " + this.plugin.getDescription().getVersion()
                + "\nConfiguration file: change desired values here, kindly pm me on spigot if you have questions @Ean224");
        config.options().copyHeader(true);

        config.addDefault("check_for_updates", true);
        config.addDefault("count_down_time", 60);
        config.addDefault("buy_price", 1000L);
        config.addDefault("max_ticket.default", 1);
        config.addDefault("max_ticket.donor", 1);
        config.addDefault("allow_broadcast", true);
        config.addDefault("play_out_title_when_win", true);
        config.addDefault("sounds.anvil_cancel_sound", true);
        config.addDefault("sounds.player_win_sound", true);
        config.addDefault("starting_money_in_pot", 0);
        config.addDefault("time.seconds", "seconds");
        config.addDefault("time.minutes", "minutes");
        config.addDefault("time.hours", "hours");
        config.addDefault("time.days", "days");
        config.addDefault("gui_name.help_gui_name", "&1&lLottery HELP MENU");
        config.addDefault("gui_name.menu_gui_name", "&b&lLottery-%number%");
        config.addDefault("gui_name.confirm_gui_name", "&1&lARE YOU SURE");
        config.addDefault("gui_name.player_gui_name", "&1&lPlayer Stats");
        config.addDefault("mysql.enabled", false);
        config.addDefault("mysql.ip", "0.0.0.0");
        config.addDefault("mysql.port", 3306);
        config.addDefault("mysql.username", "root");
        config.addDefault("mysql.password", "password");
        config.addDefault("mysql.database-name", "lotterydb");
        config.addDefault("mysql.stats_message", new String[]{
                "&aStats for %player%:",
                "&7Wins: &e%wins%",
                "&7Tickets Bought: &e%tickets%",
                "&7Money Spent: &e%money%",
                "&7Money Won: &e%money_won%"
        });
        config.addDefault("save_lottery_progress_on_stop", false);
        config.addDefault("buy_tickets_cooldown_in_seconds", 0);
        config.addDefault("open_help_menu_on_base_cmd", true);
        config.addDefault("lottery_tax_in_percentage", 0);
        config.addDefault("time.seconds_short", "s");
        config.addDefault("time.minutes_short", "m");
        config.addDefault("time.hours_short", "h");
        config.addDefault("time.days_short", "d");
        config.addDefault("bungee-sync", false);
        config.addDefault("title.fade_in_seconds", 0.5);
        config.addDefault("title.stay_seconds", 1);
        config.addDefault("title.fade_out_seconds", 0.5);
        config.addDefault("money_display_format", "#,###,##0.00");
        config.addDefault("use_text_confirmation_on_buy", false);
        config.addDefault("open_confirm_menu_on_buy", true);
        config.addDefault("use_text_confirmation_on_buy_amount", false);
        config.addDefault("open_confirm_menu_on_buy_amount", true);
        config.addDefault("shoot_fireworks_on_win", true);

        config.options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public LotteryGrabber loadGrabber() {
        FileConfiguration fc = this.plugin.getConfig();
        LotteryGrabber grabber = new LotteryGrabber();

        grabber.setShouldUpdate(fc.getBoolean("check_for_updates"));
        grabber.setCountDownTime(fc.getInt("count_down_time"));
        grabber.setBuyPrice(fc.getLong("buy_price"));
        grabber.setMaxTicketDefault(fc.getInt("max_ticket.default"));
        grabber.setMaxTicketDonor(fc.getInt("max_ticket.donor"));
        grabber.setAllowBroadcast(fc.getBoolean("allow_broadcast"));
        grabber.setShouldTitle(fc.getBoolean("play_out_title_when_win"));
        grabber.setAnvilSound(fc.getBoolean("sounds.anvil_cancel_sound"));
        grabber.setWinSound(fc.getBoolean("sounds.player_win_sound"));
        grabber.setStartingMoney(fc.getLong("starting_money_in_pot"));

        grabber.setHelpMenuName(fc.getString("gui_name.help_gui_name"));
        grabber.setLotteryMenuName(fc.getString("gui_name.menu_gui_name"));
        grabber.setConfirmMenuName(fc.getString("gui_name.confirm_gui_name"));
        grabber.setPlayerMenuName(fc.getString("gui_name.player_gui_name"));
        grabber.setBungeeSync(fc.getBoolean("bungee-sync"));
        grabber.setFadeInSeconds(fc.getDouble("title.fade_in_seconds"));
        grabber.setFadeOutSeconds(fc.getDouble("title.fade_out_seconds"));
        grabber.setStaySeconds(fc.getDouble("title.stay_seconds"));
        grabber.setMoneyFormat(fc.getString("money_display_format"));
        AdvancedLottery.lotteryGrabber = grabber;
        return grabber;
    }
}