package dev.mqzn.dmar.menus.kitsmenu;

import dev.mqzn.dmar.core.kits.base.ArmorItem;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.kits.base.KitItem;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitViewer extends Menu {

    private final Kit kit;
    public KitViewer(Kit kit) {
        super("&2View " + kit.getDisplayName() + "'s Items", 3);
        this.kit = kit;
    }

    @Override
    public void buildButtons(Player player) {
        for(ArmorItem armor : kit.getArmor()) {
            inventory.addItem(armor.toItemStack());
        }
        for(KitItem contentItem : kit.getContents()) {
            if(contentItem != null) {
                inventory.addItem(contentItem.toItemStack());
            }
        }

        inventory.setItem(this.getSize()-1, ItemBuilder.construct()
                .create(Material.BARRIER, 1)
                .setDisplay("&c<< Go Back").build());

    }

    @Override
    public void handleOnClick(InventoryClickEvent e) {
        Player clicker = (Player)e.getWhoClicked();

        ItemStack clickedItem = e.getCurrentItem();

        if(clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) return;


        if(clickedItem.getType() == Material.BARRIER) {
            clicker.closeInventory();
            MenuManager.getInstance().openMenu(clicker, new KitsMenu());
        }
        e.setCancelled(true);
    }



}
