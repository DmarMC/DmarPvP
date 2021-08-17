package dev.mqzn.dmar.core.trails.arrows;

import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.util.Formatter;
import net.minecraft.server.v1_8_R3.EnumParticle;

import java.util.Collections;
import java.util.List;

public class MusicalArrows extends ArrowsTrail {


    @Override
    public EnumParticle getParticle() {
        return EnumParticle.NOTE;
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(Formatter.color("&9Enjoy the music from your shots"));
    }

    @Override
    public String getName() {
        return "Musical Arrows";
    }

    @Override
    public String getDisplayName() {
        return "&9&lMusical &7&lArrows";
    }

    @Override
    public int getSlot() {
        return 4;
    }


}
