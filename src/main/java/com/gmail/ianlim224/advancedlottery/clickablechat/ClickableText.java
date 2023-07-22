package com.gmail.ianlim224.advancedlottery.clickablechat;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.messages.Messages;

import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ClickableText implements Clickable {

	@Override
	public void sendToPlayer(Player p, int tickets) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			TextComponent text = new TextComponent(
					Messages.BROADCAST_BUYER.getConfigValue(player)
							.replaceAll("%ticket%", tickets + "")
							.replaceAll("%player%", p.getName()));
			text.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/lottery buy"));
			text.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
					new ComponentBuilder(Messages.HOVER_CHAT_MSG.getConfigValue(p)).create()));
			player.spigot().sendMessage(text);
		}
	}

	@Override
	public void sendMessageWithAction(Player p, String msg, String hiddenMsg, Player target) {
		TextComponent text = new TextComponent(msg);
		text.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/msg " + target.getName()));
		text.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hiddenMsg).create()));
		p.spigot().sendMessage(text);
	}
}
