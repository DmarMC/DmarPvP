package dev.mqzn.dmar.core.trails.rod;

import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import java.util.Collections;
import java.util.List;

public class SlimeRod extends RodTrail {

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.SLIME;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&aHit your opponents with slime particles"));
    }

    @Override
    public String getName() {
        return "Slime Rod";
    }

    @Override
    public String getDisplayName() {
        return "&2&lSlime &a&lRod";
    }

    @Override
    public int getSlot() {
        return 4;
    }


}
