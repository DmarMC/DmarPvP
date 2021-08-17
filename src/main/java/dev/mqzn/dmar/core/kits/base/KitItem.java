package dev.mqzn.dmar.core.kits.base;

import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.io.Serializable;
import java.util.Objects;

public class KitItem implements Comparable<KitItem>, Serializable {

    private final Kit kit;
    private final Material type;
    private final int amount, slot;
    private final short damage;
    private final ItemEnchant[] enchants;
    private final ItemStack item;

    public KitItem(Kit kit, int slot, Material type, int amount, ItemEnchant... enchants) {
        this(kit, slot, type, amount, (short) 0, enchants);
    }

    private KitItem(Kit kit, int slot, Material type, int amount, short damage, ItemEnchant... enchants) {
        this.kit = kit;
        this.slot = slot;
        this.type = type;
        this.amount = amount;
        this.damage = damage;
        this.enchants = enchants;

        Enchantment[] es = new Enchantment[enchants.length];
        for (int i = 0; i < enchants.length ; i++) {
            es[i] = enchants[i].getEnchant();
        }

        int[] levels = new int[enchants.length];
        for (int i = 0; i < enchants.length ; i++) {
            levels[i] = enchants[i].getLevel();
        }

        item =  ItemBuilder.construct()
                .create(this.getType(), this.getAmount(), this.getDamage())
                .addEnchants(es)
                .setEnchantLevels(levels)
                .addFlags(ItemFlag.values())
                .setDisplay(this.getKit().getDisplayName())
                .setUnbreakable(true).build();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(KitItem o) {
        return this.slot-o.slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KitItem)) return false;
        KitItem kitItem = (KitItem) o;
        return amount == kitItem.amount &&
                damage == kitItem.damage &&
                slot == kitItem.slot &&
                Objects.equals(kit, kitItem.kit) &&
                type == kitItem.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kit, type, amount, damage, slot);
    }

    public Kit getKit() {
        return kit;
    }

    public int getSlot() {
        return slot;
    }

    public short getDamage() {
        return damage;
    }

    public int getAmount() {
        return amount;
    }

    public Material getType() {
        return type;
    }

    public ItemEnchant[] getEnchants() {
        return enchants;
    }

    public ItemStack toItemStack() {
        return item;
    }


}
