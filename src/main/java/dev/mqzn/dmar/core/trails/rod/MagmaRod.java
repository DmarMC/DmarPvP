package dev.mqzn.dmar.core.trails.rod;

import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class MagmaRod extends RodTrail {

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.DRIP_LAVA;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&eMelt your enemies with the lava pop !"));
    }

    @Override
    public String getName() {
        return "MagmaRod";
    }

    @Override
    public String getDisplayName() {
        return "&4&lMagma &c&lRod";
    }

    @Override
    public int getSlot() {
        return 2;
    }


}
