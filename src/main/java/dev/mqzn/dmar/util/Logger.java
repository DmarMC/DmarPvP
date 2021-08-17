package dev.mqzn.dmar.util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Logger {

    private static final ConsoleCommandSender console;
    private static final String[] enableMsg, disableMsg;

    static {

        enableMsg = new String[] {

                "&7-------------------------------",
                "       &cDmarPvP 1.0-BETA",
                "&2Status: &aEnabled",
                "&bAuthor: &7Mqzn",
                "&3Discord: &7Mqzn#8141",
                "&7-------------------------------"

        };

        disableMsg = new String[enableMsg.length];
        disableMsg[2] = "&cStatus: &4Disabled";
        for (int i = 0; i < enableMsg.length; i++) {
            if(i == 2) continue;
            disableMsg[i] = enableMsg[i];
        }


        console = Bukkit.getConsoleSender();

    }

    public static void plainLog(String msg) {
        console.sendMessage(Formatter.color(msg));
    }

    public static void log(String msg, LogType logType) {
        console.sendMessage(Formatter
                .color(logType.getPrefix() + msg));
    }

    public static void defaultLog(String msg) {
        console.sendMessage(Formatter.color(LogType.NOTIFY.getPrefix() + msg));
    }


    public static void logEnable() {

        for (String s : enableMsg) {
            Logger.plainLog(s);
        }
    }

    public static void logDisable() {

        for(String str : disableMsg) {
            Logger.plainLog(str);
        }
    }

    public static ConsoleCommandSender getConsole() {
        return console;
    }

    public enum LogType {


        NOTIFY("&2[PROCESS] "),
        WARN("&e[WARNING] "),
        ERROR("&c[ERROR] ");

        private final String prefix;

        LogType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

        @Override
        public String toString() {
            return "LogType{" +
                    "prefix='" + prefix + '\'' +
                    ", type='" + this.name() + '}';
        }

    }


}
