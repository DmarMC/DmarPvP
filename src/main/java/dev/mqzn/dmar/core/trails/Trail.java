package dev.mqzn.dmar.core.trails;

import dev.mqzn.dmar.core.Purchasable;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Objects;

public abstract class Trail implements Purchasable {

    private final String permission;

    public Trail() {
        permission = "pvp.trail." + this.getName()
                .toLowerCase().replace(" ", "");
    }

    public abstract EnumParticle getParticle();

    public abstract List<String> getDescription();

    public abstract String getName();

    public abstract ItemStack getMenuItem();

    public abstract void handleLaunch(ProjectileLaunchEvent e);

    public abstract void handleHit(ProjectileHitEvent e);

    public abstract void runEffect(Projectile entity);

    public abstract String getDisplayName();

    public abstract int getSlot();

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trail)) return false;
        Trail trail = (Trail) o;
        return getName().equals(trail.getName()) &&
                getDescription().equals(trail.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }

}
