package dev.mqzn.dmar.core.battlecry;

import dev.mqzn.dmar.core.Purchasable;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

interface BattleCry extends Purchasable {

    String getName();

    String getDisplayName();

    Sound getSound();

    Material getIcon();

    int getSlot();

    default String getDescription() {
        return "&7Set this sound as your battle cry";
    }


    default void execute(Player player) {
        player.playSound(player.getLocation(), this.getSound(), 1.0f, 1.0f);
    }


    default ItemStack getMenuItem(Player player) {
        String option = !player.hasPermission(this.getPermission()) ? "&8➥ &eClick to purchase !"
                : "&8➥ &aClick to select !";

        return ItemBuilder.construct()
                .create(this.getIcon(), 1).setDisplay(Formatter.color(this.getDisplayName()))
                .setLore(this.getDescription(), "", "&6Costs: &7" + this.getPrice(), "", option)
                .addEnchants(Enchantment.DAMAGE_ALL).setEnchantLevels(1)
                .addFlags(ItemFlag.values()).build();
    }

}
