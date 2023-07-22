package com.gmail.ianlim224.advancedlottery.utils;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class FileLogging {
    private static final long MAX_FILE_SIZE = 9000; // file size in bytes
    private final AdvancedLottery plugin;
    private final File logFile;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy/MM/dd HH:mm:ss] ");

    public FileLogging(AdvancedLottery plugin) {
        this.plugin = plugin;
        this.logFile = new File(plugin.getDataFolder(), "/data/lottery.log");
    }

    public void debug(String msg) {
        String time = LocalDateTime.now().format(formatter);
        checkIfLogsFileExists();

        service.execute(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(time + " " + msg + "\n");
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to read from winner.dat!", e);
            }
        });
    }

    private void checkIfLogsFileExists() {
        checkIfLogExceeded();

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create lottery.log!", e);
            }
        }
    }

    private void checkIfLogExceeded() {
        if (logFile.exists() && logFile.getTotalSpace() > MAX_FILE_SIZE) {
            logFile.delete();
        }
    }
}
