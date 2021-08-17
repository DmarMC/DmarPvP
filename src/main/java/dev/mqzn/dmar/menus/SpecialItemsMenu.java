package dev.mqzn.dmar.menus;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.Transaction;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class SpecialItemsMenu extends Menu {

    public SpecialItemsMenu() {
        super("&a&l&nSpecial Items", 3);
    }

    @Override
    public void buildButtons(Player player) {

        //11, 13, 15
        UserData data = DmarPvP.getInstance().getUserDataManager().getData(player.getUniqueId());

        DmarPvP.getInstance().getItemsManager()
                .getSpecialItemsMap().forEach((k, v) -> inventory.setItem(v.getMenuSlot(), v.getMenuItem(data)));

        inventory.setItem(14, ItemBuilder.construct().create(Material.ENDER_CHEST, 1)
                .setDisplay("&d&lComing Soon").addEnchants(Enchantment.DURABILITY)
                .setEnchantLevels(1)
                .setUnbreakable(true).addFlags(ItemFlag.values()).build());

        inventory.setItem(this.getSize()-1, ItemBuilder.construct().create(Material.BARRIER, 1)
                .setDisplay("&c<< Go Back").build());


    }

    @Override
    public void handleOnClick(InventoryClickEvent e) {

        Player player = (Player)e.getWhoClicked();

        ItemStack clicked = e.getCurrentItem();
        if(clicked == null || clicked.getType() == Material.AIR || !clicked.hasItemMeta()) return;

        SpecialItem specialItem = fromSlot(e.getSlot());

        if(clicked.getType() == Material.BARRIER) {
            player.closeInventory();
            MenuManager.getInstance().openMenu(player, new ShopMenu());
            e.setCancelled(true);
            return;
        }

        if(specialItem == null || clicked.getType() == Material.OBSIDIAN || clicked.getType() == Material.ENDER_CHEST) {
            e.setCancelled(true);
            return;
        }

        Transaction.process(player, specialItem);
        e.setCancelled(true);

    }

    private SpecialItem fromSlot(int slot) {
        for (Map.Entry<String, SpecialItem> entry : DmarPvP.getInstance().getItemsManager().getSpecialItemsMap().entrySet()) {
            SpecialItem v = entry.getValue();
            if(slot == v.getMenuSlot()) {
                return v;
            }
        }
        return null;
    }



}
