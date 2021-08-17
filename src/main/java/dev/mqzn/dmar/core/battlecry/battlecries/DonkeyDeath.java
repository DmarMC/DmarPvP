package dev.mqzn.dmar.core.battlecry.battlecries;

import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import org.bukkit.Material;
import org.bukkit.Sound;

public class DonkeyDeath extends BaseBattleCry {

    @Override
    public String getName() {
        return "donkey";
    }

    @Override
    public String getDisplayName() {
        return "&c&lDonkeyDeath";
    }

    @Override
    public Sound getSound() {
        return Sound.DONKEY_DEATH;
    }

    @Override
    public Material getIcon() {
        return Material.LEASH;
    }

    @Override
    public int getSlot() {
        return 16;
    }


}
