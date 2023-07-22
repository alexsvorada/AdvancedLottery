package com.gmail.ianlim224.advancedlottery.mysql;

import java.util.UUID;

public class PlayerData {
    private final int wins;
    private final int tickets;
    private final double money;
    private final double moneyWon;
    private final UUID uuid;

    public PlayerData(int wins, int tickets, double money, double moneyWon, UUID uuid) {
        this.wins = wins;
        this.tickets = tickets;
        this.money = money;
        this.moneyWon = moneyWon;
        this.uuid = uuid;
    }

    public int getWins() {
        return wins;
    }

    public int getTickets() {
        return tickets;
    }

    public double getMoney() {
        return money;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getMoneyWon() {
        return moneyWon;
    }
}
