package dev.mqzn.dmar.menus.trails;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.Transaction;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.managers.TrailsManager;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ArrowsTrailsMenu extends Menu {
    public ArrowsTrailsMenu() {
        super("&e&nArrows Trails Menu", 2);
    }

    @Override
    public void buildButtons(Player player) {

        MenuDesigner.design(this).fillAll(GlassColor.DARK_GREY, false);

        TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();
        for(ArrowsTrail arrowsTrail : trailsManager.getArrowTrials()) {
            inventory.setItem(arrowsTrail.getSlot(), arrowsTrail.getMenuItem());
        }

        inventory.setItem(this.getSize()-1, ItemBuilder.construct().create(Material.BARRIER, 1)
                .addFlags(ItemFlag.values()).setDisplay("&c<< Go Back").build());

        inventory.setItem(13, ItemBuilder.construct().create(Material.FEATHER, 1)
                .setDisplay("&b&lClick to unequip your arrow trail").build());
    }

    @Override
    public void handleOnClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(item == null || item.getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }

        TrailsManager manager = DmarPvP.getInstance().getTrailsManager();

        ArrowsTrail t = null;
        for(ArrowsTrail trail : manager.getArrowTrials()) {
            if(item.equals(trail.getMenuItem())) {
                t = trail;
            }
        }

        if(t != null) {
            Transaction.process(player, t);
        }else {
            if(item.getType() == Material.BARRIER) {
                player.closeInventory();
                MenuManager.getInstance().openMenu(player, new TrailsMenu());
            }

            if(item.getType() == Material.FEATHER) {
                player.closeInventory();

                UserDataManager userManager = DmarPvP.getInstance().getUserDataManager();
                UserData data = userManager.getData(player.getUniqueId());

                if(data.getArrowsTrail() != null) {
                    data.setArrowsTrail(null);
                    userManager.updateCached(data);
                    Formatter.sendMsg(player, "&eYou have unequipped your arrows trail");
                }else {
                    Formatter.sendMsg(player, "&cYou don't have an arrow trail !");
                }
            }
        }
        e.setCancelled(true);
    }


}
