package dev.mqzn.dmar.core.perks;

import dev.mqzn.dmar.core.perks.base.ConsumablePerk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.function.Consumer;

public class FlashPerk extends ConsumablePerk {



    @Override
    public Consumer<Player> getActions() {
        return (player)
                -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30*20, 1));
    }

    @Override
    public String getName() {
        return "flash";
    }

    @Override
    public String getDisplayName() {
        return "&e&lFlash Perk";
    }

    @Override
    public String getDescription() {
        return "Makes you move like flash !";
    }

    @Override
    public Material getIcon() {
        return Material.FEATHER;
    }

    @Override
    public int getSlot() {
        return 2;
    }

    @Override
    public int getPrice() {
        return 42000;
    }


}
