package dev.mqzn.dmar.core.events;

import dev.mqzn.dmar.core.board.api.AssembleBoard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class AssembleBoardCreatedEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();

    private final AssembleBoard board;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    /**
     * Assemble Board Created Event.
     *
     * @param board of player.
     */
    public AssembleBoardCreatedEvent(AssembleBoard board) {
        this.board = board;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
