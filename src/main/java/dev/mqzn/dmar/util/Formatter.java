package dev.mqzn.dmar.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Formatter {

    public static final String PREFIX;

    static {
        PREFIX = "&4&lDmar&c&lPvP &8» ";
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> colorList(List<String> list) {
        return list.stream().map(Formatter::color).collect(Collectors.toList());
    }

    public static void sendMsg(Player player, String msg) {
        player.sendMessage(Formatter.color(PREFIX+msg));
    }

    public static String formatDouble(double d, int decimalPlaces) {


        StringBuilder b = new StringBuilder();
        for (int i = 0; i < decimalPlaces; i++) {
            b.append("#");
        }

        DecimalFormat format = new DecimalFormat("#." + b.toString());
        return format.format(d);
    }

    public static void sendMsg(Player player, Msg msg) {
        Formatter.sendMsg(player, msg.toString());
    }

}
