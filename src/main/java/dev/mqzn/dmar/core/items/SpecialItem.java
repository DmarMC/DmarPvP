package dev.mqzn.dmar.core.items;

import dev.mqzn.dmar.core.Purchasable;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SpecialItem implements Purchasable {

    private final ItemStack item;
    private final Material icon;

    protected final static String TIME_DESC = "&eItem will expire in 10 minutes from time you purchased it";

    public SpecialItem(Material icon) {
        this.icon = icon;
        this.item = ItemBuilder.construct().create(icon, 1)
                .setDisplay(this.getDisplayName()).setLore(this.getDescription())
                .addEnchants(Enchantment.DAMAGE_ALL).setEnchantLevels(2)
                .setUnbreakable(true).addFlags(ItemFlag.values()).build();
    }

    protected Material getIcon() {
        return icon;
    }

    public ItemStack getItem() {
        return item;
    }


    public abstract String getName();

    public abstract String getDisplayName();

    public abstract int getMenuSlot();

    public abstract String getDescription();

    public Material getIcon(UserData data) {
        return !data.hasItem(this)
                ? getIcon() : Material.OBSIDIAN;
    }

    public  ItemStack getMenuItem(UserData data) {


        List<String> lore = data.hasItem(this)
                ? new ArrayList<>() : Arrays.asList(this.getDescription(), "", TIME_DESC, "", "&6Cost: &7" + this.getPrice(), "", "&8➥ &aClick to purchase !");

        return ItemBuilder.construct().create(this.getIcon(data), 1)
                .setDisplay(data.hasItem(this) ? "&cAlready Equipped !" : this.getDisplayName())
                .setLore(lore).addEnchants(Enchantment.DAMAGE_ALL)
                .setEnchantLevels(1).addFlags(ItemFlag.values()).build();

    }

    public void apply(Player player) {
        int slot = -1;

        int size = player.getInventory().getSize();
        for (int i = 0; i < size; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item == null || item.getType() == Material.AIR) continue;
            if(item.getType().name().endsWith("SWORD")) {
                slot = i;
                break;
            }
        }
        if(slot == -1) {
            slot = player.getInventory().firstEmpty();
        }

        player.getInventory().setItem(slot,  this.getItem());
        player.updateInventory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialItem)) return false;
        SpecialItem specialItem = (SpecialItem) o;
        return specialItem.getName().equals(this.getName());
    }

    @Override
    public String getPermission() {
        return "pvp.item." + this.getName().toLowerCase();
    }


}
