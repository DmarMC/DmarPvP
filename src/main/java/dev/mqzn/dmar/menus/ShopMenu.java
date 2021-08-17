package dev.mqzn.dmar.menus;

import dev.mqzn.dmar.backend.db.DataUpdate;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.perks.base.PerkSlotItem;
import dev.mqzn.dmar.core.perks.base.SlotItemStatus;
import dev.mqzn.dmar.menus.api.GlassColor;
import dev.mqzn.dmar.menus.api.Menu;
import dev.mqzn.dmar.menus.api.MenuDesigner;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.menus.kitsmenu.KitsMenu;
import dev.mqzn.dmar.menus.trails.TrailsMenu;
import dev.mqzn.dmar.util.BigCache;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopMenu extends Menu {

    private final int[] perkSlots = {10, 19, 28};


    public ShopMenu() {
        super("&f&l>> &9&lPvP Shop &f&l<<", 6);
    }



    @Override
    public void buildButtons(Player player) {

        MenuDesigner.design(this).fillSquared(GlassColor.DARK_GREY, GlassColor.GREY, false);


        inventory.setItem(12, ItemBuilder.construct().create(Material.DIAMOND_CHESTPLATE, 1)
                .addFlags(ItemFlag.values()).setDisplay("&7&kII&3&lPVP &b&lKITS&7&k&lII")
                .setLore("&7Left-Click to view the kits").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());


        inventory.setItem(14, ItemBuilder.construct().create(Material.NOTE_BLOCK, 1)
                        .addFlags(ItemFlag.values()).setDisplay("&e&k&lII&4&lBATTLE CRY&e&k&lII")
                        .setLore("&7Left-Click to view the perks").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());



        inventory.setItem(30, ItemBuilder.construct().create(Material.RED_ROSE, 1)
                .addFlags(ItemFlag.values()).setDisplay("&8&kII&f&lPVP Trails&8&kII")
                .setLore("&7Left-Click to view the trails").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());

        inventory.setItem(32, ItemBuilder.construct().create(Material.GOLD_AXE, 1)
                .addFlags(ItemFlag.values()).setDisplay("&8&kII&a&lSpecial &2&lItems&8&kII")
                .setLore("&7Left-Click to view special items in pvp").addEnchants(Enchantment.DURABILITY).setEnchantLevels(1).build());

        //perks items slots
        // 10, 19, 28

        UserData data = DmarPvP.getInstance().getUserDataManager().getData(player.getUniqueId());

        for (int slot = 1; slot <= 3; slot++) {
            SlotItemStatus status;

            Perk perk = data.getPerkAt(slot);
            if(perk == null) {
                if(player.hasPermission("perk.slot." + slot)) {
                    status = SlotItemStatus.EMPTY;
                }else {
                    status = SlotItemStatus.NO_PERMISSION;
                }
            }else {
                status = SlotItemStatus.FULL;
            }

            inventory.setItem(perkSlots[slot-1], new PerkSlotItem(player, perk, status, slot));

        }


    }



    @Override
    public void handleOnClick(InventoryClickEvent e) {
        Player clicker = (Player)e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();

        if(clicked == null || clicked.getType() == Material.AIR
                || !clicked.hasItemMeta()) return;


        switch (clicked.getType()) {
            case DIAMOND_CHESTPLATE:
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new KitsMenu());
                break;
            case RED_ROSE:
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new TrailsMenu());
                break;

            case NOTE_BLOCK:
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new BattleCryMenu());
                break;

            case GOLD_AXE:
                clicker.closeInventory();
                MenuManager.getInstance().openMenu(clicker, new SpecialItemsMenu());
                break;
            default: {

                Set<String> icons = DmarPvP.getInstance().getPerkManager().getPerks().values()
                        .parallelStream().map(Perk::getDisplayName).map(Formatter::color).collect(Collectors.toSet());

                if(clicked.getType() == Material.BEDROCK || icons.contains(Formatter.color(clicked.getItemMeta().getDisplayName()))) {

                    int slot = getPerkSlotOrder(e.getSlot());
                    SlotItemStatus status = clicked.getType() == Material.BEDROCK ? SlotItemStatus.EMPTY : SlotItemStatus.FULL;

                    if(e.getClick() == ClickType.RIGHT && status == SlotItemStatus.FULL) {
                        UserData data = DmarPvP.getInstance().getUserDataManager().getData(clicker.getUniqueId());
                        Map<Integer, Perk> selectedPerks=  data.getSelectedPerks();
                        selectedPerks.remove(slot);
                        data.setSelectedPerks(selectedPerks);

                        DmarPvP.getInstance().getUserDataManager().update(data, DataUpdate.SELECTED_PERKS);
                        clicker.closeInventory();
                        MenuManager.getInstance().openMenu(clicker, new ShopMenu());

                        e.setCancelled(true);
                        return;
                    }

                    if(e.getClick() == ClickType.LEFT) {

                        Perk perk = null;
                        for (Perk p : DmarPvP.getInstance().getPerkManager().getPerks().values()) {
                            if (p.getIcon() == clicked.getType()) {
                                perk = p;
                                break;
                            }
                        }

                        DmarPvP.getInstance().getPerkSlotEdits()
                                .put(clicker.getUniqueId(), new BigCache<>(slot, perk, status));

                        clicker.closeInventory();
                        MenuManager.getInstance().openMenu(clicker, new PerksMenu());
                    }
                }
                break;

            }


        }

        e.setCancelled(true);
    }


    private int getPerkSlotOrder(int slotClickedInGui) {

        for (int i = 1; i < perkSlots.length+1 ; i++) {
            if(perkSlots[i-1] == slotClickedInGui) {
                return i;
            }
        }

        return -1;
    }

}
