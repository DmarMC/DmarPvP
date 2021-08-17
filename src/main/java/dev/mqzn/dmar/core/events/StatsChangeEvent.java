package dev.mqzn.dmar.core.events;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.combat.BattleResult;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatsChangeEvent extends Event {


    private final static HandlerList handlerList = new HandlerList();

    private final UserData newStats;
    private final Player player;
    private final BattleResult battleResult;

    public StatsChangeEvent(Player player, BattleResult battleResult, UserData newStats) {
        this.newStats = newStats;
        this.player = player;
        this.battleResult = battleResult;
    }

    public BattleResult getBattleResult() {
        return battleResult;
    }

    public Player getPlayer() {
        return player;
    }

    public UserData getNewStats() {
        return newStats;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }


    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
