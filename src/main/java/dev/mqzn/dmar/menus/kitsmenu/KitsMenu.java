package dev.mqzn.dmar.menus.kitsmenu;


import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.Purchasable;
import dev.mqzn.dmar.core.Transaction;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.menus.ShopMenu;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


public class KitsMenu extends Menu {


    public KitsMenu() {
        super("&4&l&nPvP Kits", 2);
    }

    @Override
    public void buildButtons(Player player) {

        MenuDesigner.design(this).fillAll(GlassColor.DARK_GREY, false);

        for(Kit kit : DmarPvP.getInstance().getKitManager().getKits()) {
            inventory.setItem(kit.getSlot(), kit.getMenuItem());
        }

        inventory.setItem(13, ItemBuilder.construct().create(Material.BARRIER, 1)
                .setDisplay("&c<< Go Back").build());
    }

    @Override
    public void handleOnClick(InventoryClickEvent e) {

        UserDataManager userManager = DmarPvP.getInstance().getUserDataManager();

        Player clicker = (Player) e.getWhoClicked();
        UserData userData = userManager.getData(clicker.getUniqueId());
        ItemStack clickedItem = e.getCurrentItem();

        if(clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) return;

        Kit kit = Kit.fromSlot(e.getSlot());

        if(kit == null) {
            if(clickedItem.getType() == Material.BARRIER) {
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new ShopMenu());
            }
            e.setCancelled(true);
            return;
        }

        if (e.isLeftClick()) {
            //the user is trying to choose
            if (kit.isPurchasable()) {
                Transaction.process(clicker, (Purchasable) kit);
                clicker.closeInventory();

            } else {
                clicker.closeInventory();
                if (clicker.hasPermission(kit.getPermission())) {
                    kit.apply(clicker);
                    userData.setKit(kit);
                    userManager.updateCached(userData);
                    Formatter.sendMsg(clicker, "&7You have equipped the kit &a" + kit.getDisplayName());
                } else {
                    Formatter.sendMsg(clicker, "&cYou need " + kit.getDisplayName().substring(0, kit.getDisplayName().indexOf(' ')) + " Rank to choose the kit !");
                }
            }
        } else if (e.isRightClick()) {
            clicker.closeInventory();
            KitViewer kitViewer = new KitViewer(kit);
            DmarPvP.getInstance().getKitViewerManager().setKitViewer(clicker, kitViewer);
            kitViewer.open(clicker);
        }
        e.setCancelled(true);
    }
}
