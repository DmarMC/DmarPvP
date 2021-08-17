package dev.mqzn.dmar.core.kits.base;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.KitManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.util.ItemBuilder;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.Purchasable;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.Objects;
import java.util.Set;

public abstract class Kit implements Comparable<Kit> {

    private final ArmorItem[] armor;
    private final Set<KitItem> contents;
    private final ItemStack menuItem;

    public Kit() {
        armor = this.getArmor();

        contents = this.getContents();

        String[] desc = new String[5];
        desc[0] = Formatter.color("&7&l&m------------------");
        desc[4] = desc[0];
        desc[1] = Formatter.color(this.getDescription());
        desc[2] = Formatter.color("&aLeft-Click to choose");
        desc[3] = Formatter.color("&bRight-Click to view kit items");

        menuItem = ItemBuilder.construct().create(this.getIcon(), 1)
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL).setEnchantLevels(1)
                .addFlags(ItemFlag.values()).setDisplay(this.getDisplayName())
                .setLore(desc).build();
    }

    public abstract String getUniqueName();

    public abstract int getSlot();

    public abstract String getDisplayName();

    public abstract int getWeight();

    public String getPermission() {
        return "pvp.kit." + this.getUniqueName().toLowerCase();
    }

    public abstract String getDescription();

    public abstract Set<KitItem> getContents();

    public abstract ArmorItem[] getArmor();

    public abstract Material getIcon();

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public boolean isPurchasable() {
        return (this instanceof Purchasable) && ((Purchasable)this).getPrice() > 0;
    }

    public void apply(Player player) {

        player.getInventory().clear();
        KitManager manager = DmarPvP.getInstance().getKitManager();

        FireItem fireItem = new FireItem(this, 8, 1);
        manager.getFireCounterMap().put(player.getUniqueId(), fireItem);

        contents.add(fireItem);
        contents.forEach( (item) ->
                player.getInventory().setItem(item.getSlot(),
                        item.toItemStack()));

        player.getInventory().setHelmet(armor[0].toItemStack());
        player.getInventory().setChestplate(armor[1].toItemStack());
        player.getInventory().setLeggings(armor[2].toItemStack());
        player.getInventory().setBoots(armor[3].toItemStack());

        player.updateInventory();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kit)) return false;
        Kit kit = (Kit) o;
        return this.getUniqueName().equalsIgnoreCase(kit.getUniqueName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUniqueName());
    }

    @Override
    public int compareTo(Kit o) {
        return o.getWeight()-this.getWeight();
    }


    protected void addItem(Set<KitItem> items, int slot, Material type, int amount) {
        items.add(new KitItem(this, slot, type, amount));
    }

    protected void addItem(Set<KitItem> items, int slot, Material type) {
        items.add(new KitItem(this, slot, type, 1));
    }

    protected void addItem(Set<KitItem> items, int slot, Material type, ItemEnchant... enchant) {
        items.add(new KitItem(this, slot, type, 1, enchant));
    }

    public static Kit fromSlot(int slot) {
        for(Kit kit : DmarPvP.getInstance().getKitManager().getKits()) {
            if(kit.getSlot() == slot) {
                return kit;
            }
        }
        return null;
    }

}
