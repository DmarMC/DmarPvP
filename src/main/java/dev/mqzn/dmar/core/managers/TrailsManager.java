package dev.mqzn.dmar.core.managers;

import com.google.common.collect.Maps;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.trails.Trail;
import dev.mqzn.dmar.core.trails.arrows.FlamedArrows;
import dev.mqzn.dmar.core.trails.arrows.LoveArrows;
import dev.mqzn.dmar.core.trails.arrows.MusicalArrows;
import dev.mqzn.dmar.core.trails.arrows.SmokeyArrows;
import dev.mqzn.dmar.core.trails.rod.LoveRod;
import dev.mqzn.dmar.core.trails.rod.MagmaRod;
import dev.mqzn.dmar.core.trails.rod.SlimeRod;
import dev.mqzn.dmar.core.trails.rod.SmokeyRod;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrailsManager {

    private final Map<String, Trail> trails;

    public TrailsManager() {
        this.trails = Maps.newConcurrentMap();
        this.storeTrails();
    }

    private void storeTrails() {
        this.addTrail(new FlamedArrows());
        this.addTrail(new LoveArrows());
        this.addTrail(new MusicalArrows());
        this.addTrail(new SmokeyArrows());

        this.addTrail(new LoveRod());
        this.addTrail(new MagmaRod());
        this.addTrail(new SlimeRod());
        this.addTrail(new SmokeyRod());
    }

    private void addTrail(Trail trail) {
        this.trails.put(trail.getName(), trail);
    }

    public Map<String, Trail> getTrails() {
        return trails;
    }

    public List<ArrowsTrail> getArrowTrials() {

        List<ArrowsTrail> arrowsTrails = new ArrayList<>();
        for(Trail trail : this.getTrails().values()) {
            if(trail instanceof ArrowsTrail){
                ArrowsTrail arrowsTrail = (ArrowsTrail) trail;
                arrowsTrails.add(arrowsTrail);
            }
        }

        return arrowsTrails;
    }

    public List<RodTrail> getRodTrails() {

        List<RodTrail> rodTrails = new ArrayList<>();
        for (Trail trail : this.getTrails().values()) {
            if(trail instanceof RodTrail) {
                RodTrail rodTrail = (RodTrail)trail;
                rodTrails.add(rodTrail);
            }
        }

        return rodTrails;
    }


    public boolean playerHasTrial(Player player, Trail trail, UserDataManager manager) {


        UserData data = manager.getData(player.getUniqueId());
        if(data == null) return false; //impossible to happen, but just in case !

        if(trail instanceof RodTrail) {
            RodTrail rodTrail = (RodTrail)trail;

            if(data.getRodTrail() == null) return false;

            return data.getRodTrail().equals(rodTrail);
        }

        if(trail instanceof ArrowsTrail) {
            ArrowsTrail arrowsTrail = (ArrowsTrail)trail;

            if(data.getArrowsTrail() == null) return false;

            return data.getArrowsTrail().equals(arrowsTrail);
        }

        return false;
    }


    public Trail getTrail(String name) {
        return this.trails.getOrDefault(name, null);
    }

}
