package dev.mqzn.dmar.menus.trails;

import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.menus.ShopMenu;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TrailsMenu extends Menu {

    public TrailsMenu() {
        super("&d&l&nPVP TRAILS", 3);
    }

    @Override
    public void buildButtons(Player player) {
        MenuDesigner.design(this).fillAll(GlassColor.DARK_GREY, false);

        this.inventory.setItem(12, ItemBuilder.construct().create(Material.ARROW, 1)
                .addFlags(ItemFlag.values()).setDisplay("&7&l&kII&9&lARROWS TRAILS&7&l&kII")
                .setLore("&7Left-Click to view arrow trails !").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());

        this.inventory.setItem(14, ItemBuilder.construct().create(Material.FISHING_ROD, 1)
                .addFlags(ItemFlag.values()).setDisplay("&7&l&kII&6&lROD TRAILS&7&l&kII")
                .setLore("&7Left-Click to view arrow trails !").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());

        this.inventory.setItem(this.getSize()-1, ItemBuilder.construct().create(Material.BARRIER, 1)
                .addFlags(ItemFlag.values()).setDisplay("&c<< Go Back").build());

    }


    @Override
    public void handleOnClick(InventoryClickEvent e) {

        Player player = (Player)e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(item == null || item.getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }

        switch (item.getType()) {
            case FISHING_ROD:
                MenuManager.getInstance().openMenu(player, new RodTrailsMenu());
                break;
            case ARROW:
                MenuManager.getInstance().openMenu(player, new ArrowsTrailsMenu());
                break;
            case BARRIER:
                MenuManager.getInstance().openMenu(player, new ShopMenu());
                break;
        }


        e.setCancelled(true);

    }


}

