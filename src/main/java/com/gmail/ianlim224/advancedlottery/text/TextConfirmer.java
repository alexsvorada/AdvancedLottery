package com.gmail.ianlim224.advancedlottery.text;

import com.gmail.ianlim224.advancedlottery.object.TicketTransaction;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TextConfirmer {

    private static TextConfirmer instance;

    private TextConfirmer() {
    }

    private final Map<Player, TicketTransaction> transactionMemo = new HashMap<>();

    public void addPendingConfirmation(Player player, TicketTransaction transaction) {
        transactionMemo.put(player, transaction);
    }

    public static TextConfirmer getInstance() {
        if (instance == null) {
            instance = new TextConfirmer();
        }
        return instance;
    }

    public boolean hasPendingConfirmation(Player player) {
        return this.transactionMemo.containsKey(player);
    }

    public TicketTransaction completePendingConfirmation(Player player) {
        if (!transactionMemo.containsKey(player)) {
            throw new IllegalStateException("Player does not have a confirmation");
        }
        TicketTransaction transaction = this.transactionMemo.get(player);
        transactionMemo.remove(player);
        return transaction;
    }

}
