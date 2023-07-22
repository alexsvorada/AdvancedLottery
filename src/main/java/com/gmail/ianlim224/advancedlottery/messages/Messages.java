package com.gmail.ianlim224.advancedlottery.messages;

import com.gmail.ianlim224.advancedlottery.parser.Parser;
import com.gmail.ianlim224.advancedlottery.parser.PluginParser;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import org.bukkit.entity.Player;

public enum Messages {
	LOTTERY_WIN("lottery_win", "&5Congrats to &e%player% &5for winning &c$%money%"),
	BUY_SUCCESS("buy_sucess", "&6Congrats, you have successfully bought %ticket% ticket for &2$%money%, &6you now have &2$%balance%"),
	NOT_ENOUGH_MONEY("not_enough_money" , "&4You do not have enough money!"),
	ALREADY_BOUGHT("already_bought" , "&4You have already bought the maximum number of tickets"),
	WRONG_USAGE("wrong_usage", "&cWrong usage: do &e/lottery help &cfor help!"),
	TIME_TO_DRAW("time_to_draw", "&bTime to draw is &e%time%"),
	BROADCAST_NO_WINNER("broadcast_no_winner" , "&2There were no winners!"),
	BROADCAST_BUYER("broadcast_buyer" , "&d%player% has just bought %ticket% lottery ticket(s)!"),
	WINNER_TITLE_MESSAGE("winner_title_message" , "&b&l%player% &ewon the lottery!"),
	WINNER_SUBTITLE_MESSAGE("winner_subtitle_message" , "&dWith an amount of &6$%money%"),
	NOT_ENOUGH_PERMISSIONS("not_enough_permissions","&4You have insufficient permissions!"),
	LOTTERY_REMINDER("lottery_reminder" , "&dTime left to draw is less than %time%!"),
	LOTTERY_COUNTDOWN("lottery_countdown" , "&dThe lottery will be drawn in &e%time% &dseconds"),
	HOVER_CHAT_MSG("hover_chat_msg", "&bClick me to buy a ticket"),
	CLICK_ME_TEXT("click_me_text" , "&b&l[Click Me]"),
	CLICK_ME_HOVER_TEXT("click_me_hover_text" , "&7Click me to send a message to %player%"),
	TOO_MANY_TICKETS("too_many_tickets" , "&c&lOOPS! &7You cannot buy that much of tickets"),
	MYSQL_NOT_SUPPORTED("mysql_not_supported", "&4Mysql is not supported!"),
	RETRIEVING_DATA("retrieving_data", "&aRetrieving data for player %player%..."),
	PLAYER_NOT_FOUND("player_not_found", "&cThat player cannot be found"),
	TICKET_MONEY_REFUNDED("ticket_money_refunded", "&aDue to server closing, players who bought tickets have been refunded!"),
	BUY_TICKET_COOLDOWN("buy_ticket_cooldown", "&c[Cooldown] &7Please wait for another &e%time% &7seconds!"),
	PLAYER_NOT_ONLINE("player_not_online", "&cCan't send message. &e%player% &cis not online."),
	FREE_TICKETS_GIVEN("free_tickets_given", "&aChristmas came earlier this year! Here's %ticket% ticket(s) from your beloved admins :D"),
	BUY_TEXT_CONFIRM("buy_text_confirm", "&bYou're attempting to purchase %ticket% ticket(s) for a total cost of %price%. Please type &e/lottery buy confirm &bto continue."),
	NO_PENDING_CONFIRMATIONS("no_pending_confirmation", "&cYou do not have any pending confirmations!");
	private String path;
	private String value;
	private static FileConfiguration fc;
	private static Parser parser = new PluginParser()
			;
	
	private Messages(String path, String val) {
		this.path = path;
		value = val;
	}

	public static void setParser(Parser parser) {
		Messages.parser = parser;
	}

	public String getPath() {
		return path;
	}
	
	public String getValue() {
		return value;
	}
	
	public static void setFc(FileConfiguration newFc) {
		fc = newFc;
	}

	public String getConfigValue(Player player) {
		String configVal = fc.getString(this.path);
		String val = AdvancedLottery.f(configVal != null ? configVal : value);
		return parser.parse(player, val);
	}
}
