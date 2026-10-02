package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RotationFileHandler implements MessageHandler {
    private final String baseName;
    private final ChronoUnit chronoUnit;
    private final DateTimeFormatter fileDateFormatter;

    private LocalDateTime nextRotationTime;
    private String currentFileName;

    public RotationFileHandler(String baseName, ChronoUnit chronoUnit) {
        this.baseName = baseName;
        this.chronoUnit = chronoUnit;

        if (chronoUnit == ChronoUnit.MINUTES) {
            this.fileDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        } else if (chronoUnit == ChronoUnit.HOURS) {
            this.fileDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH");
        } else {
            this.fileDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        updateFileName(LocalDateTime.now());
    }

    private void updateFileName(LocalDateTime now) {

        if (nextRotationTime == null || !now.isBefore(nextRotationTime)) {

            this.currentFileName = baseName + "_" + now.format(fileDateFormatter) + ".log";

            this.nextRotationTime = now.truncatedTo(chronoUnit).plus(1, chronoUnit);
        }
    }

    @Override
    public void handle(String message) {
        LocalDateTime now = LocalDateTime.now();

        updateFileName(now);

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFileName, true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
