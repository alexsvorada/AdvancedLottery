package com.gmail.ianlim224.advancedlottery.clickablechat;

import org.bukkit.entity.Player;

public interface Clickable {
	public void sendToPlayer(Player p, int ticket);

	public void sendMessageWithAction(Player p, String msg, String hiddenMsg, Player target);
}
