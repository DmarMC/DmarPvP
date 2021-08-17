package dev.mqzn.dmar.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private Enchantment[] enchants = new Enchantment[0];

    private static ItemBuilder instance;

    public static ItemBuilder construct() {
        if(instance == null) {
            instance = new ItemBuilder();
        }
        return instance;

    }

    public ItemBuilder create(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        return instance;
    }

    public ItemBuilder create(Material material, int amount, short damage) {
        itemStack = new ItemStack(material, amount, damage);
        return instance;
    }

    public ItemBuilder setDisplay(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Formatter.color(name));
        itemStack.setItemMeta(meta);

        return instance;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Formatter.colorList(Arrays.asList(lore)));
        itemStack.setItemMeta(meta);

        return instance;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Formatter.colorList(lore));
        itemStack.setItemMeta(meta);

        return instance;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(flags);
        itemStack.setItemMeta(meta);

        return instance;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        itemStack.setItemMeta(meta);

        return instance;
    }

    public ItemBuilder addEnchants(Enchantment... enchants) {
        this.enchants = enchants;
        return instance;
    }

    public ItemBuilder setEnchantLevels(int... levels) {

        if(levels == null) return instance;

        if(enchants.length >= 1) {

            ItemMeta meta = itemStack.getItemMeta();

            for (int i = 0; i < enchants.length ; i++) {
                meta.addEnchant(enchants[i], levels[i], true);
            }
            itemStack.setItemMeta(meta);
        }

        return instance;
    }



    public ItemStack build() {
        instance = null;  // clean up
        return itemStack;
    }


}
