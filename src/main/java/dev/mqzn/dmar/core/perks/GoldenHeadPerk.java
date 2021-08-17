package dev.mqzn.dmar.core.perks;

import dev.mqzn.dmar.core.perks.base.ConsumablePerk;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.Head;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Arrays;
import java.util.function.Consumer;

public class GoldenHeadPerk extends ConsumablePerk {

    private final PotionEffect[] effects;

    public GoldenHeadPerk() {

        super();
        combatItem = this.getCombatItem();
        effects = new PotionEffect[] {
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4*20, 1),
                new PotionEffect(PotionEffectType.REGENERATION, 3*20, 1),
                new PotionEffect(PotionEffectType.SPEED, 8*20, 2)
        };

    }

    @Override
    public Consumer<Player> getActions() {
        return (player) -> player.addPotionEffects(Arrays.asList(effects));
    }

    public void giveHead(Player player) {
        if(player.getInventory().first(this.combatItem) >= 0) return;
        player.getInventory().addItem(this.combatItem);
    }

    @Override
    public String getName() {
        return "goldenhead";
    }

    @Override
    public String getDisplayName() {
        return Formatter.color("&6&lGolden&e&lHead");
    }

    @Override
    public String getDescription() {
        return "&7You get it each Kill to boost your health";
    }

    @Override
    public Material getIcon() {
        return Material.SKULL_ITEM;
    }

    @Override
    public ItemStack getCombatItem() {
        return Head.GOLDEN_HEAD.getHeadItem(this.getDisplayName(), this.getDescription()) ;
    }

    public ItemStack getCachedCombatItem() {
        return combatItem;
    }

    @Override
    public ItemStack getMenuItem(Player player) {
        return Head.GOLDEN_HEAD.getHeadItem(this.getDisplayName(),
                Enchantment.DURABILITY, this.getModifiedDescription(player));
    }

    @Override
    public int getSlot() {
        return 3;
    }

    @Override
    public int getPrice() {
        return 57500;
    }

}
