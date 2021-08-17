package dev.mqzn.dmar.core.items;

import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class ProtectiveArmor extends SpecialItem{

    public ProtectiveArmor() {
        super(Material.DIAMOND_CHESTPLATE);
    }

    @Override
    public String getName() {
        return "protective_shield";
    }

    @Override
    public String getDisplayName() {
        return "&9Protective&fShield";
    }

    @Override
    public int getMenuSlot() {
        return 16;
    }

    @Override
    public String getDescription() {
        return "A Full protection 3 diamond armor ";
    }

    @Override
    public int getPrice() {
        return 70000;
    }

    @Override
    public void apply(Player player) {

        player.getInventory().setHelmet(ItemBuilder.construct()
                .create(Material.DIAMOND_HELMET, 1).setDisplay(this.getDisplayName())
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL)
                .setEnchantLevels(3).setUnbreakable(true).addFlags(ItemFlag.values()).build());


        player.getInventory().setChestplate(ItemBuilder.construct()
                .create(Material.DIAMOND_CHESTPLATE, 1).setDisplay(this.getDisplayName())
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL)
                .setEnchantLevels(3).setUnbreakable(true).addFlags(ItemFlag.values()).build());

        player.getInventory().setLeggings(ItemBuilder.construct()
                .create(Material.DIAMOND_LEGGINGS, 1).setDisplay(this.getDisplayName())
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL)
                .setEnchantLevels(3).setUnbreakable(true).addFlags(ItemFlag.values()).build());


        player.getInventory().setBoots(ItemBuilder.construct()
                .create(Material.DIAMOND_BOOTS, 1).setDisplay(this.getDisplayName())
                .addEnchants(Enchantment.PROTECTION_ENVIRONMENTAL)
                .setEnchantLevels(3).setUnbreakable(true).addFlags(ItemFlag.values()).build());

        player.updateInventory();
    }

}
