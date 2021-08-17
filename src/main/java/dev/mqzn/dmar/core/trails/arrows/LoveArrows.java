package dev.mqzn.dmar.core.trails.arrows;

import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class LoveArrows extends ArrowsTrail {


    @Override
    public EnumParticle getParticle() {
        return EnumParticle.HEART;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&bShoot your enemies with love !"));
    }

    @Override
    public String getName() {
        return "Love Arrows";
    }

    @Override
    public String getDisplayName() {
        return "&d&lLove &5&lArrows";
    }

    @Override
    public int getSlot() {
        return 2;
    }


}
