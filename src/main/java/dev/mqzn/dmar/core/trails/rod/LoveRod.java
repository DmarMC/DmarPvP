package dev.mqzn.dmar.core.trails.rod;

import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class LoveRod extends RodTrail {

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.HEART;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&bGive love to everyone including your enemies"));
    }

    @Override
    public String getName() {
        return "Love Rod";
    }

    @Override
    public String getDisplayName() {
        return "&d&lLove &5&lRod";
    }

    @Override
    public int getSlot() {
        return 0;
    }
}
