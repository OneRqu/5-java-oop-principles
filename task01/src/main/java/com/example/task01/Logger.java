package com.example.task01;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private final String name;
    private static Logger[] loggers = new Logger[0];
    private Level currentLevel = Level.DEBUG;

    public Logger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public enum Level {
        DEBUG(100),
        INFO(200),
        WARNING(300),
        ERROR(400);

        private final int priority;

        Level(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public static Logger getLogger(String name) {
        int loggersLength = loggers.length;

        if (loggersLength > 0) {
            for (Logger logger : loggers) {
                if (logger.getName().equals(name)) {
                    return logger;
                }
            }
        }

        Logger[] newLoggers = new Logger[loggersLength + 1];

        System.arraycopy(loggers, 0, newLoggers, 0, loggersLength);

        Logger logger = new Logger(name);
        newLoggers[loggersLength] = logger;

        loggers = newLoggers;

        return logger;
    }

    public void setLevel(Level level) {
        currentLevel = level;
    }

    public Level getLevel() {
        return currentLevel;
    }

    private void writeLog(Level level, String message) {
        if (level.priority < currentLevel.priority) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.printf("[%s] %s %s %s - %s%n", level, date, time, name, message);
    }

    public void log(Level level, String message) {
        writeLog(level, message);
    }

    public void log(Level level, String message, Object... args)
    {
        String formattedMessage = MessageFormat.format(message, args);

        writeLog(level, formattedMessage);
    }

    public void debug(String message)
    {
        log(Level.DEBUG, message);
    }

    public void debug(String message, Object... args)
    {
        log(Level.DEBUG, message, args);
    }

    public void info(String message)
    {
        log(Level.INFO, message);
    }

    public void info(String message, Object... args)
    {
        log(Level.INFO, message, args);
    }

    public void warning(String message)
    {
        log(Level.WARNING, message);
    }

    public void warning(String message, Object... args)
    {
        log(Level.WARNING, message, args);
    }

    public void error(String message)
    {
        log(Level.ERROR, message);
    }

    public void error(String message, Object... args)
    {
        log(Level.ERROR, message, args);
    }
}
