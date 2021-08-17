package dev.mqzn.dmar.util;

import org.bukkit.ChatColor;

public enum  Msg {

    NO_PERMISSION(ChatColor.RED + "You don't have permission to do that !"),
    UNFOUND_PLAYER(ChatColor.RED + "Target must be online or valid !");

    private final String msg;
    Msg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

}
