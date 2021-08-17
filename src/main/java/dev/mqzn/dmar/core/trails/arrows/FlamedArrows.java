package dev.mqzn.dmar.core.trails.arrows;

import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;

import java.util.Collections;
import java.util.List;

public class FlamedArrows extends ArrowsTrail {


    @Override
    public EnumParticle getParticle() {
        return EnumParticle.FLAME;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&cShoot your enemies with hot flames"));
    }

    @Override
    public String getName() {
        return "Flamed Arrows";
    }

    @Override
    public String getDisplayName() {
        return "&6&lFlamed &e&lArrows";
    }

    @Override
    public int getSlot() {
        return 0;
    }


}

