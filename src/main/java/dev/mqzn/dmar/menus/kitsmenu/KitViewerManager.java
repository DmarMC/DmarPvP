package dev.mqzn.dmar.menus.kitsmenu;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class KitViewerManager {

    private final Map<Player, KitViewer> kitViewerMap;

    public KitViewerManager() {
        kitViewerMap = new HashMap<>();
    }

    public KitViewer getKitViewer(Player player) {
        return kitViewerMap.getOrDefault(player, null);
    }

    public void setKitViewer(Player player, KitViewer kitViewer) {
        this.kitViewerMap.put(player, kitViewer);
    }

}
