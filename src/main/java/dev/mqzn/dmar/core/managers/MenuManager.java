package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.menus.api.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuManager {

    private static MenuManager instance;
    private final Map<UUID, Menu> openMenus;
    private MenuManager() {
        openMenus = new ConcurrentHashMap<>();
        /*titles = new String[] {
                "&f&l>> &9&lPvP Shop &f&l<<",
                "&6&l&nPVP PERKS",
                "&c&l&nBattle Cries",
                "&a&l&nSpecial Items",
                "&d&l&nPVP TRAILS",
                "&6&nRod Trails Menu",
                "&e&nArrows Trails Menu",
                "&4&l&nPvP Kits"
        };*/
    }


    public static MenuManager getInstance() {
        if(instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    public void handleClickMenus(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        Menu menu = this.openMenus.get(player.getUniqueId());

        if(menu != null) {
            System.out.println("HANDLING CLICK IN " + menu.getTitle());
            menu.handleOnClick(e);
        }

    }

    public Menu getOpenMenu(UUID viewer) {
        return this.openMenus.get(viewer);
    }

    public void registerMenu(UUID viewerId, Menu menu) {
        this.openMenus.put(viewerId, menu);
    }

    public void unregister(UUID id, Menu menu) {
        this.openMenus.remove(id, menu);
    }

    public void openMenu(Player player, Menu menu) {
        this.registerMenu(player.getUniqueId(), menu);
        menu.open(player);
    }


}
