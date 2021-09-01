package dev.mqzn.dmar.core.kits;

import com.google.common.collect.Sets;
import dev.mqzn.dmar.core.kits.base.ArmorItem;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.kits.base.KitItem;
import org.bukkit.Material;
import java.util.Set;

public class PremiumKit extends Kit {

    @Override
    public String getUniqueName() {
        return "premium";
    }

    @Override
    public int getSlot() {
        return 5;
    }

    @Override
    public String getDisplayName() {
        return "&6Premium Kit";
    }

    @Override
    public int getWeight() {
        return 3;
    }

    @Override
    public String getDescription() {
        return "&ePremium Kit &crequires &6Premium &cRank";
    }

    @Override
    public Set<KitItem> getContents() {
        Set<KitItem> items = Sets.newHashSet();

        this.addItem(items, 0, Material.IRON_SWORD);
        this.addItem(items, 1, Material.FISHING_ROD);
        this.addItem(items, 2, Material.BOW);
        this.addItem(items, 7, Material.ARROW, 5);

        return items;
    }

    @Override
    public ArmorItem[] getArmor() {
        return new ArmorItem[] {
                new ArmorItem(this, Material.DIAMOND_HELMET),
                new ArmorItem(this, Material.IRON_CHESTPLATE),
                new ArmorItem(this, Material.IRON_LEGGINGS),
                new ArmorItem(this, Material.DIAMOND_BOOTS)
        };
    }

    @Override
    public Material getIcon() {
        return Material.IRON_CHESTPLATE;
    }

}
