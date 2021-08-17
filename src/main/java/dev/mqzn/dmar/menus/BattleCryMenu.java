package dev.mqzn.dmar.menus;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import dev.mqzn.dmar.core.managers.BattleCryManager;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BattleCryMenu extends Menu {

    private final BattleCryManager manager = DmarPvP.getInstance().getBattleCryManager();

    public BattleCryMenu() {
        super("&c&l&nBattle Cries", 3);
    }

    @Override
    public void buildButtons(Player player) {

        MenuDesigner.design(this).fillAll(GlassColor.DARK_GREY, false);

        manager.getBattleCryMap().values()
                .forEach(bc -> inventory.setItem(bc.getSlot(), bc.getMenuItem(player)));

        inventory.setItem(this.getSize()-1, ItemBuilder.construct().create(Material.BARRIER, 1)
                .setDisplay("&c<< Go Back").build());
    }

    @Override
    public void handleOnClick(InventoryClickEvent e) {

        Player player = (Player)e.getWhoClicked();

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) return;

        boolean foundBC = false;
        for (BaseBattleCry bc : manager.getBattleCryMap().values()) {
            if (bc.getSlot() == e.getSlot()) {

                foundBC = true;

                UserDataManager dataManager = DmarPvP.getInstance().getUserDataManager();
                UserData data = dataManager.getData(player.getUniqueId());
                data.setBattleCry(bc);
                dataManager.fullUpdate(data);

                player.closeInventory();
                Formatter.sendMsg(player, "&7You have chosen " + bc.getDisplayName() + " as your battle cry !");
                break;
            }

        }

        if(!foundBC && clickedItem.getType() == Material.BARRIER) {
            player.closeInventory();
            MenuManager.getInstance().openMenu(player, new ShopMenu());
        }

        e.setCancelled(true);

    }
}
