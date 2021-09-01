package dev.mqzn.dmar.core.kits;

import com.google.common.collect.Sets;
import dev.mqzn.dmar.core.kits.base.ArmorItem;
import dev.mqzn.dmar.core.Purchasable;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.kits.base.KitItem;
import org.bukkit.Material;
import java.util.Set;

public class VipKit extends Kit implements Purchasable {

    @Override
    public String getUniqueName() {
        return "vip";
    }

    @Override
    public int getSlot() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return "&bVIP Kit";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public String getDescription() {
        return "&b&lVIP KIT &6Costs: " + this.getPrice() + " coins";
    }

    @Override
    public Set<KitItem> getContents() {
        Set<KitItem> items = Sets.newHashSet();
        this.addItem(items, 0, Material.STONE_SWORD);
        this.addItem(items, 1, Material.FISHING_ROD);
        this.addItem(items, 2, Material.BOW);
        this.addItem(items, 7, Material.ARROW, 5);

        return items;
    }

    @Override
    public ArmorItem[] getArmor() {
        return new ArmorItem[] {
                new ArmorItem(this, Material.IRON_HELMET),
                new ArmorItem(this, Material.IRON_CHESTPLATE),
                new ArmorItem(this, Material.IRON_LEGGINGS),
                new ArmorItem(this, Material.IRON_BOOTS)
        };
    }

    @Override
    public Material getIcon() {
        return Material.GOLD_CHESTPLATE;
    }


    @Override
    public int getPrice() {
        return 25000;
    }

    @Override
    public String getPermission() {
        return super.getPermission();
    }

}
