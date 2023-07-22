package com.gmail.ianlim224.advancedlottery.object;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class LotteryPot {
	private double moneyInPot;
	private double startingMoney;
	private static LotteryPot instance = null;
	
	private LotteryPot(AdvancedLottery plugin) { 
		startingMoney = plugin.getConfig().getLong("starting_money_in_pot");
		moneyInPot = startingMoney;
	}
	
	public double getMoneyInPot() {
		return moneyInPot;
	}

	public void setMoneyInPot(double moneyInPot) {
		this.moneyInPot = moneyInPot;
	}
	
	public void clearMoneyInPot() {
		setMoneyInPot(startingMoney);	
	}
	
	public void addMoneyInPot(double amount) {
		this.moneyInPot = this.moneyInPot + amount;
	}
	
	public static LotteryPot getInstance(AdvancedLottery plugin) {
		if(instance != null) {
			return instance;
		}else {
			instance = new LotteryPot(plugin);
		}
		return instance;
	}
	
	public static LotteryPot reload(AdvancedLottery plugin) {
		instance = new LotteryPot(plugin);
		return instance;
	}
}
