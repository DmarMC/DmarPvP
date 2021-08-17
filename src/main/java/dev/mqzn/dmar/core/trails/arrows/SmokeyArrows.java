package dev.mqzn.dmar.core.trails.arrows;

import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class SmokeyArrows extends ArrowsTrail {

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.SMOKE_NORMAL;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&7Distract your enemies with smoke"));
    }

    @Override
    public String getName() {
        return "Smokey Arrows";
    }

    @Override
    public String getDisplayName() {
        return "&7&lSmokey &8Arrows";
    }

    @Override
    public int getSlot() {
        return 6;
    }

}
