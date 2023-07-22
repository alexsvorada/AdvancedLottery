package com.gmail.ianlim224.advancedlottery.object;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class TicketTransaction {
	private final double totalPrice;
	private final int amount;
	
	public TicketTransaction(int amount, AdvancedLottery plugin) {
		this.totalPrice = LotteryTicket.getInstance(plugin).getTicketCost() * amount;
		this.amount = amount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public int getAmount() {
		return amount;
	}
}
