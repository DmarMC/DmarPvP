package dev.mqzn.dmar.util;

import dev.mqzn.dmar.DmarPvP;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitScheduler;

public class Tasks {

    private static final BukkitScheduler scheduler;
    private static final DmarPvP plugin;

    static {
        scheduler = Bukkit.getScheduler();
        plugin = DmarPvP.getInstance();
    }


    public static void runAsync(Runnable runnable) {
        scheduler.runTaskAsynchronously(plugin, runnable);
    }

    public static void runTimerAsync(Runnable runnable, long delay, long period){
        scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }


    public static void runLaterAsync(Runnable runnable, long delay) {
        scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
    

}
