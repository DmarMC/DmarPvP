package dev.mqzn.dmar.menus;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.Transaction;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.core.managers.PerkManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.perks.base.PerkSlotEdits;
import dev.mqzn.dmar.core.perks.base.SlotItemStatus;
import dev.mqzn.dmar.exceptions.PerkSlotOutOfBounds;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PerksMenu extends Menu {

    public PerksMenu() {
        super("&6&l&nPVP PERKS", 2);
    }

    private final PerkManager perkManager = DmarPvP.getInstance().getPerkManager();

    @Override
    public void buildButtons(Player player) {

        MenuDesigner.design(this).fillAll(GlassColor.DARK_GREY, false);

        for(Perk p : perkManager.getPerks().values()) {
            inventory.setItem(p.getSlot(), p.getMenuItem(player));
        }

        inventory.setItem(this.getSize()-1, ItemBuilder.construct().create(Material.BARRIER, 1)
                .setDisplay("&c<< Go Back").build());


    }


    @Override
    public void handleOnClick(InventoryClickEvent e) {

        Player clicker = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) return;


        Perk perk = Perk.fromSlot(e.getSlot());
        if(perk == null)  {
            if(clickedItem.getType() == Material.BARRIER) {
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new ShopMenu());
            }
            e.setCancelled(true);
            return;
        }

        PerkSlotEdits edits = DmarPvP.getInstance().getPerkSlotEdits();
        if(edits.containsKey(clicker.getUniqueId())) {
            UserDataManager dataManager = DmarPvP.getInstance().getUserDataManager();
            UserData userData = dataManager.getData(clicker.getUniqueId());

            if(userData.getSelectedPerks().containsValue(perk)) {
                clicker.closeInventory();
                Formatter.sendMsg(clicker, "&cYou already have that perk in another slot !");
                return;
            }

            BigCache<Integer, Perk, SlotItemStatus> cache = edits.get(clicker.getUniqueId());

            try {
                perkManager.setPerkAt(userData, perk, cache.getFirst());
            } catch (PerkSlotOutOfBounds ex) {
                clicker.closeInventory();
                Formatter.sendMsg(clicker, "&cYou can't have more than 3 perks !");
                return;
            }

            if(cache.getThird() == SlotItemStatus.FULL && cache.getSecond() != null ) {

                //IT WAS FULL ALREADY
                // getting the changed perk

                Formatter.sendMsg(clicker, "&7You have changed the perk in  &cSlot #" +
                        cache.getFirst() + " &7from " + cache.getSecond().getDisplayName() + " &7to " +
                        perk.getDisplayName());

            }else {
                // the status cannot be NO PERMISSION, So it's EMPTY for sure
                // && item.getPerk() == null

                Formatter.sendMsg(clicker, "&7You have set the perk in &cSlot #" + cache.getFirst() + " &7to " + perk.getDisplayName());
            }

            clicker.closeInventory();

            Tasks.runAsync(()-> dataManager.fullUpdate(userData));

            return;
        }

        Transaction.process(clicker, perk);
        clicker.closeInventory();
        MenuManager.getInstance().openMenu(clicker, new ShopMenu());

        e.setCancelled(true);
    }




}
