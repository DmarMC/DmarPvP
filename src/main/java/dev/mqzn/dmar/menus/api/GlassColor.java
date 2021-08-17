package dev.mqzn.dmar.menus.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public enum GlassColor {

    RED((byte)14),
    BROWN((byte)12),
    GREEN((byte)5),
    DARK_GREEN((byte)13),
    BLUE((byte)11),
    YELLOW((byte)3),
    GOLD((byte)1),
    AQUA((byte)3),
    CYAN((byte)9),
    PINK((byte)6),
    PURPLE((byte)2),
    GREY((byte)8),
    DARK_GREY((byte)7),
    WHITE((byte)0);

    private final byte colorId;
    GlassColor(byte colorId) {
        this.colorId = colorId;
    }

    public byte getColorId() {
        return colorId;
    }

    public ItemStack toItemStack(boolean glow) {

        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, this.getColorId());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(Collections.emptyList());

        if(glow) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);

        return item;
    }

}
