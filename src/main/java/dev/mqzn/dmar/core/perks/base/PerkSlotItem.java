package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerkSlotItem extends ItemStack {

    private final Perk perk;
    private final int slot;
    private final SlotItemStatus status;

    public PerkSlotItem(Player player, Perk perk, SlotItemStatus status, int slot) throws IllegalArgumentException {
        super(Objects.requireNonNull(getPerkItemFrom(player, perk, status, slot)));
        this.slot = slot;
        this.perk = perk;
        this.status = status;
    }

    public Perk getPerk() {
        return perk;
    }

    public SlotItemStatus getStatus() {
        return status;
    }

    public static ItemStack getPerkItemFrom(Player player, Perk perk, SlotItemStatus status, int slot) {

        switch (status) {

            case NO_PERMISSION:
                return getNoPermissionSlot(slot);
            case FULL:
                return getPerkSlotItem(player, perk, slot);
            case EMPTY:
                return getEmptyPerkItem(slot);
        }

        return null;

    }

    @Deprecated
    private static Material getType(Perk perk, SlotItemStatus status) {
        switch (status) {
            case FULL:
                return perk.getIcon();
            case EMPTY:
                return Material.BEDROCK;

            default: {
                return Material.BARRIER;
            }
        }
        
    }

    public static ItemStack getEmptyPerkItem(int slot) {
        return ItemBuilder.construct()
                .create(Material.BEDROCK, 1)
                .setDisplay("&ePerk Slot &6#" + slot).setLore("&2Status: &aEMPTY")
                .addFlags(ItemFlag.values()).addEnchants(Enchantment.DURABILITY)
                .setEnchantLevels(1).build();
    }

    public static ItemStack getNoPermissionSlot(int slot) {
        String requirement = slot == 2 ? "&6Premium&7" : "&aEmerald&7";
        return ItemBuilder.construct()
                .create(Material.BARRIER, 1)
                .setDisplay("&7Perk Slot &2#" + slot)
                .setLore(" &cRequires " + requirement + " Rank or &eHigher &7!", "&2Status: &4Not Accessible")
                .build();
    }

    public static ItemStack getPerkSlotItem(Player player, Perk perk, int slot) {

        if(perk==null) return getEmptyPerkItem(slot);

        ItemStack perkItem = perk.getMenuItem(player);
        ItemMeta meta = perkItem.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(perk.getDescription());
        lore.add("&e&lRight-CLICK &7to &9UnEquip &7!");

        meta.setLore(Formatter.colorList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.values());
        perkItem.setItemMeta(meta);

        return perkItem;
    }

    public int getSlot() {
        return slot;
    }

}
