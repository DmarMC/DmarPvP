package dev.mqzn.dmar.core.battlecry.battlecries;

import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import org.bukkit.Material;
import org.bukkit.Sound;

public class FireworkWhizz extends BaseBattleCry {


    @Override
    public String getName() {
        return "fireworkwhizz";
    }

    @Override
    public String getDisplayName() {
        return "&b&lFirework Whizz";
    }

    @Override
    public Sound getSound() {
        return Sound.FIREWORK_TWINKLE;
    }

    @Override
    public Material getIcon() {
        return Material.FIREWORK;
    }

    @Override
    public int getSlot() {
        return 13;
    }


}
