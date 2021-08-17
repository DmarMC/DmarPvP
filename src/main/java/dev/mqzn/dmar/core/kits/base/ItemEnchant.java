package dev.mqzn.dmar.core.kits.base;

import org.bukkit.enchantments.Enchantment;
import java.util.Objects;

public class ItemEnchant {

    private final Enchantment enchant;
    private final int level;

    public ItemEnchant(Enchantment enchantment, int level) {
        this.enchant = enchantment;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Enchantment getEnchant() {
        return enchant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEnchant)) return false;
        ItemEnchant that = (ItemEnchant) o;
        return getLevel() == that.getLevel() &&
                Objects.equals(getEnchant(), that.getEnchant());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEnchant(), getLevel());
    }

}
