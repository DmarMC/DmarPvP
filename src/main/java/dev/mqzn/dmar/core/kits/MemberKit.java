package dev.mqzn.dmar.core.kits;

import dev.mqzn.dmar.core.kits.base.ArmorItem;
import dev.mqzn.dmar.core.kits.base.KitItem;
import dev.mqzn.dmar.core.kits.base.Kit;
import org.bukkit.Material;
import java.util.HashSet;
import java.util.Set;

public class MemberKit extends Kit {

    @Override
    public String getUniqueName() {
        return "member";
    }

    @Override
    public int getSlot() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "&7Member Kit";
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "&eThe free default member kit";
    }

    @Override
    public Set<KitItem> getContents() {

        Set<KitItem> items = new HashSet<>();
        this.addItem(items, 0, Material.STONE_SWORD);
        this.addItem(items, 1, Material.FISHING_ROD);
        this.addItem(items, 2, Material.BOW);
        this.addItem(items, 7, Material.ARROW, 4);

        return items;
    }

    @Override
    public ArmorItem[] getArmor() {
        return new ArmorItem[] {
                new ArmorItem(this, Material.CHAINMAIL_HELMET),
                new ArmorItem(this, Material.IRON_CHESTPLATE),
                new ArmorItem(this, Material.IRON_LEGGINGS),
                new ArmorItem(this, Material.CHAINMAIL_BOOTS)
        };
    }

    @Override
    public Material getIcon() {
        return Material.CHAINMAIL_CHESTPLATE;
    }

}
