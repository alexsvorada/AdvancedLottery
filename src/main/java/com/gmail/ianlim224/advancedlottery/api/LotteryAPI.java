package com.gmail.ianlim224.advancedlottery.api;

import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.object.LotteryPot;
import com.gmail.ianlim224.advancedlottery.object.LotteryTicket;

public class LotteryAPI {

	/**
	 * gets the count down time
	 * 
	 * @return the count down time in milliseconds
	 */

	public long getTimeLong() {
		return AdvancedLottery.getInstance().getLotteryTimer().timeLeft();
	}

	/**
	 * gets the formatted count down time, e.g: n seconds
	 * 
	 * @return the formatted time
	 */
	public String getTimeString() {
		return AdvancedLottery.getInstance().getLotteryTimer().time(false);
	}

	/**
	 * gets the formatted count down time with shortened suffix, e.g: n seconds
	 *
	 * @return the formatted time
	 */
	public String getTimeShortString() {
		return AdvancedLottery.getInstance().getLotteryTimer().time(true);
	}

	/**
	 * gets the money available in the pot
	 * 
	 * @return the money in pot
	 */
	public double getMoneyInPot() {
		return LotteryPot.getInstance(AdvancedLottery.getInstance()).getMoneyInPot();
	}

	/**
	 * gets the players who bought tickets
	 * 
	 * @return a set containing buyers' UUID
	 */
	public Set<UUID> getBuyers() {
		return LotteryTicket.getInstance(AdvancedLottery.getInstance()).getPlayers();
	}

	/**
	 * gets the tickets bought by player
	 * 
	 * @param player
	 *            the player to check
	 *            
	 * @return the number of tickets a player has bought
	 */
	public int getTickets(Player player) {
		return LotteryTicket.getInstance(AdvancedLottery.getInstance()).getTicketsBought(player.getUniqueId());
	}

}
