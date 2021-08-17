package dev.mqzn.dmar.core.events;

import dev.mqzn.dmar.core.combat.Battle;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PostCombatTriggerEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();

    private final Battle battle;

    public PostCombatTriggerEvent(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
