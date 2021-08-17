package dev.mqzn.dmar.core.perks;

import dev.mqzn.dmar.core.perks.base.CombatAssistPerk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.function.Consumer;

public class MurderPerk extends CombatAssistPerk {

    @Override
    public Consumer<Player> setActions() {
        return (player) -> {
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
                            5*20, 0));
        };
    }

    @Override
    public boolean getHealthCondition(Player player) {
        return player.getHealth() < 10.0;
    }

    @Override
    public String getName() {
        return "murder";
    }

    @Override
    public String getDisplayName() {
        return "&cMurder Perk";
    }

    @Override
    public String getDescription() {
        return "&7Gives you strength while your health is lower than half";
    }

    @Override
    public Material getIcon() {
        return Material.IRON_SWORD;
    }

    @Override
    public int getSlot() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 15000;
    }


}
