package dev.mqzn.dmar.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public enum Head {

    DARK_HEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhjNTNiY2U4YWU1OGRjNjkyNDkzNDgxOTA5YjcwZTExYWI3ZTk0MjJkOWQ4NzYzNTEyM2QwNzZjNzEzM2UifX19"),
    GOLDEN_HEAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI4NjhlYzZkYmRiYjZiZWNmNjk4ZGExMzZlN2E2Y2QyOGUxOTMxNDc5NmNlMjZhM2Y2N2Q2YWI2NTZlYjIxOSJ9fX0=");

    private final String token;
    Head(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    public  ItemStack getHeadItem(String displayName, Enchantment enchantment, String... lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM , 1, (byte) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", this.getToken()));
        Field field;

        try {
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for(ItemFlag itemFlag : ItemFlag.values()) {
            skullMeta.addItemFlags(itemFlag);
        }

        if(enchantment != null) {
            skullMeta.addEnchant(enchantment, 1, true);
        }
        skullMeta.setDisplayName(Formatter.color(displayName));
        skullMeta.setLore(Formatter.colorList(Arrays.asList(lore)));
        skull.setItemMeta(skullMeta);

        return skull;
    }

    public  ItemStack getHeadItem(String displayName, String... lore) {
        return this.getHeadItem(displayName, null, lore);
    }


}