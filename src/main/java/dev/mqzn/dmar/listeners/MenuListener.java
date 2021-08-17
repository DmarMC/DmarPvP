package dev.mqzn.dmar.listeners;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.menus.PerksMenu;
import dev.mqzn.dmar.menus.api.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e)  {

        /*Player player = (Player)e.getWhoClicked();

        Inventory inv = e.getInventory();
        String invTitle = Formatter.color(inv.getTitle());

        DmarPvP instance = DmarPvP.getInstance();
        ShopMenu shopMenu = instance.getShopMenu();
        KitsMenu kitsMenu = instance.getKitsMenu();
        PerksMenu perksMenu = instance.getPerksMenu();
        TrailsMenu trailsMenu = instance.getTrailsMenu();
        ArrowsTrailsMenu arrowsTrailsMenu = instance.getArrowsTrailsMenu();
        RodTrailsMenu rodTrailsMenu = instance.getRodTrailsMenu();

        if(invTitle.equalsIgnoreCase(Formatter.color(shopMenu.getTitle()))) {
            shopMenu.handleOnClick(e);
        }else if(invTitle.equalsIgnoreCase(Formatter.color(kitsMenu.getTitle()))) {
            kitsMenu.handleOnClick(e);
        }else if(invTitle.startsWith(Formatter.color("&2View")) ) {
            instance.getKitViewerManager().getKitViewer(player).handleOnClick(e);
        }else if(invTitle.equalsIgnoreCase(Formatter.color(perksMenu.getTitle()))) {
            perksMenu.handleOnClick(e);
        }else if(invTitle.equalsIgnoreCase(Formatter.color(trailsMenu.getTitle()))) {
            trailsMenu.handleOnClick(e);
        } else if (invTitle.equalsIgnoreCase(Formatter.color(rodTrailsMenu.getTitle()))) {
            rodTrailsMenu.handleOnClick(e);
        }else if(invTitle.equalsIgnoreCase(Formatter.color(arrowsTrailsMenu.getTitle()))) {
            arrowsTrailsMenu.handleOnClick(e);
        }*/

        MenuManager.getInstance().handleClickMenus(e);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();

        Menu menu = MenuManager.getInstance().getOpenMenu(player.getUniqueId());

        if(menu instanceof PerksMenu) {
            DmarPvP.getInstance().getPerkSlotEdits().remove(player.getUniqueId());
        }

        MenuManager.getInstance().unregister(player.getUniqueId(), menu);

    }


}
