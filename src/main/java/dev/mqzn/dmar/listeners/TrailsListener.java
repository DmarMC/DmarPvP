package dev.mqzn.dmar.listeners;


import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.UserDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class TrailsListener implements Listener {

    private final UserDataManager dataManager;

    {
        dataManager = DmarPvP.getInstance().getUserDataManager();
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {

        if(!(e.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player)e.getEntity().getShooter();

        UserData data = dataManager.getData(player.getUniqueId());

        if(data == null) return;

        RodTrail rodTrail = data.getRodTrail();
        if(rodTrail != null) {
            rodTrail.handleLaunch(e);
            rodTrail.runEffect(e.getEntity());
        }

        ArrowsTrail arrowsTrail = data.getArrowsTrail();
        if(arrowsTrail != null) {
            arrowsTrail.handleLaunch(e);
            arrowsTrail.runEffect(e.getEntity());
        }

    }


    @EventHandler
    public void onHit(ProjectileHitEvent e) {

        if(!(e.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player)e.getEntity().getShooter();

        UserData data = dataManager.getData(player.getUniqueId());

        if(data == null) return;

        RodTrail rodTrail = data.getRodTrail();
        if(rodTrail != null) {
            rodTrail.handleHit(e);
        }

        ArrowsTrail arrowsTrail = data.getArrowsTrail();
        if(arrowsTrail != null) {
            arrowsTrail.handleHit(e);
        }

    }



}
