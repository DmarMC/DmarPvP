package dev.mqzn.dmar.core.battlecry.battlecries;

import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import org.bukkit.Material;
import org.bukkit.Sound;

public class Achievement extends BaseBattleCry {

    @Override
    public String getName() {
        return "achievement";
    }

    @Override
    public String getDisplayName() {
        return "&aAchievement";
    }

    @Override
    public Sound getSound() {
        return Sound.LEVEL_UP;
    }

    @Override
    public Material getIcon() {
        return Material.EXP_BOTTLE;
    }

    @Override
    public int getSlot() {
        return 10;
    }


}
