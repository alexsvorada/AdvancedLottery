package com.gmail.ianlim224.advancedlottery.time;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import com.gmail.ianlim224.advancedlottery.object.LotteryPot;
import com.gmail.ianlim224.advancedlottery.utils.SpigotCommons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Reminder implements Comparable<Reminder> {
    public static final String NOT_ANNOUNCED = SpigotCommons.f("&c&l[NOT ANNOUNCED]");
    public static final String HAS_ANNOUNCED = SpigotCommons.f("&a&l[HAS ANNOUNCED]");
    private final Time time;
    private final AdvancedLottery plugin;
    private BukkitTask task;

    public Reminder(final Time time, AdvancedLottery plugin) {
        this.time = time;
        this.task = null;
        this.plugin = plugin;

        if (plugin.getLotteryTimer().getDuration() - time.toMilis() > 0) {
            schedule();
        }
    }

    public boolean hasAnnounced() {
        return task == null;
    }

    public void cancel() {
        if (task != null)
            task.cancel();

        task = null;
    }

    public void schedule() {
        if (!hasAnnounced())
            throw new IllegalStateException("Previous task have not ended yet");

        task = new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String msg = Messages.LOTTERY_REMINDER.getConfigValue(p).replaceAll("%time%", time.toString())
                            .replaceAll("%money%", SpigotCommons.formatMoney(LotteryPot.getInstance(plugin).getMoneyInPot()));

                    for (String s : msg.split("%line%")) {
                        p.sendMessage(s);
                    }
                }
                Reminder.this.cancel();
            }
        }.runTaskLater(plugin, new Time(plugin.getLotteryTimer().getDuration() - time.toMilis(), false, plugin).toTicks());
    }

    public Time getTime() {
        return time;
    }

    public long getMilis() {
        return time.toMilis();
    }

    @Override
    public String toString() {
        return time.toString() + " " + (hasAnnounced() ? HAS_ANNOUNCED : NOT_ANNOUNCED);
    }

    @Override
    public int compareTo(Reminder r) {
        return (int) (time.toMilis() - r.time.toMilis());
    }
}
