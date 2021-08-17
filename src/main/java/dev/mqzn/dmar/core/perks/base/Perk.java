package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.Purchasable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public abstract class Perk implements Purchasable, Comparable<Perk> {

    private final String permission;
    private final String[] desc;

    public Perk() {
        this.permission = "pvp.perk." + this.getName().toLowerCase();

        desc = new String[4];
        desc[0] = Formatter.color("&7&l&m------------------");
        desc[3] = desc[0];
        desc[1] = Formatter.color(this.getDescription());

    }

    public static Perk fromSlot(int slot) {

        for(Perk perk : DmarPvP.getInstance().getPerkManager().getPerks().values()) {
            if(perk.getSlot() == slot) {
                return perk;
            }
        }

        return null;
    }

    public static Perk fromItemStack(ItemStack item) {

        String displayName = item.getItemMeta().getDisplayName();
        for(Perk perk : DmarPvP.getInstance().getPerkManager().getPerks().values()) {

            if(Formatter.color(perk.getDisplayName())
                    .equals(Formatter.color(displayName))) {
                return perk;
            }
        }

        return null;
    }

    public ItemStack getMenuItem(Player player) {

        return ItemBuilder.construct().create(this.getIcon(), 1)
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL).setEnchantLevels(1)
                .addFlags(ItemFlag.values()).setDisplay(this.getDisplayName())
                .setLore(this.getModifiedDescription(player)).build();
    }

    public String[] getModifiedDescription(Player player) {
        desc[2] = Formatter.color(!player.hasPermission(this.getPermission()) ? "&8➥ &eClick to purchase !"
                : "&8➥ &aClick to select !");

        return desc;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public abstract String getName();

    public abstract String getDisplayName();

    public abstract String getDescription();

    public abstract Material getIcon();

    public abstract int getSlot();


    @Override
    public int compareTo(Perk o) {
        return this.getPrice()-o.getPrice();
    }

}
