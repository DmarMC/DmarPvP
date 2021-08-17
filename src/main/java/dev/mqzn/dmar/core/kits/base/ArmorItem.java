package dev.mqzn.dmar.core.kits.base;

import org.bukkit.Material;

public class ArmorItem extends KitItem{

    public ArmorItem(Kit kit, Material type, ItemEnchant... enchants) {
        super(kit, -1, type, 1, enchants);
    }

}
