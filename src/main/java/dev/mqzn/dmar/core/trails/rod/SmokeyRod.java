package dev.mqzn.dmar.core.trails.rod;

import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class SmokeyRod extends RodTrail {

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.SMOKE_NORMAL;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&7Cover your enemy with Smoke"));
    }

    @Override
    public String getName() {
        return "Smokey Rod";
    }

    @Override
    public String getDisplayName() {
        return "&7&lSmokey &8Rod";
    }

    @Override
    public int getSlot() {
        return 6;
    }
}
