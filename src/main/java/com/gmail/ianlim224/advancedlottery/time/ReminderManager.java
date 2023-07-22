package com.gmail.ianlim224.advancedlottery.time;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ReminderManager {
    private final Map<Long, Reminder> reminders;
    private final File remindersFile;
    private final AdvancedLottery plugin;

    public ReminderManager(AdvancedLottery plugin) {
        this.reminders = new HashMap<>();
        this.remindersFile = new File(plugin.getDataFolder(), "reminders.dat");
        this.plugin = plugin;
    }

    public void addReminder(Reminder reminder) {
        if (reminders.containsKey(reminder.getMilis()))
            return;
        reminders.put(reminder.getMilis(), reminder);
    }

    public void removeReminder(Reminder reminder) {
        reminders.remove(reminder.getMilis());
    }

    public boolean hasReminder(Reminder reminder) {
        return reminders.containsKey(reminder.getMilis());
    }

    public void read() {
        checkIfRemindersFileExists();
        try (BufferedReader reader = new BufferedReader(new FileReader(remindersFile))) {
            String s;
            while ((s = reader.readLine()) != null) {
                TimeParser parser = new TimeParser(s, plugin);
                addReminder(new Reminder(parser.getTime(), plugin));
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to read from reminders.dat!", e);
        }
    }

    public void clean() {
        for (Reminder reminder : reminders.values()) {
            reminder.cancel();
        }
        reminders.clear();
    }

    public void save() {
        checkIfRemindersFileExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(remindersFile, false))) {
            for (Reminder reminder : reminders.values()) {
                writer.write(reminder.getTime().toString() + "\n");
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to write to reminders.dat!", e);
        }
    }

    private void checkIfRemindersFileExists() {
        if (!remindersFile.exists()) {
            try {
                remindersFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create reminders.dat!", e);
            }
        }
    }

    public void reload() {
        for (Reminder reminder : reminders.values()) {
            reminder.cancel();
            reminder.schedule();
        }
    }

    public Reminder[] getReminders() {
        return reminders.values().toArray(new Reminder[0]);
    }
}
