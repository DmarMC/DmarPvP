package dev.mqzn.dmar.core.trails;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;

public abstract class ArrowsTrail extends Trail {

    private final ArrayList<Arrow> arrows;

    public ArrowsTrail() {
        this.arrows = new ArrayList<>();
    }

    @Override
    public ItemStack getMenuItem() {

        String[] lore = new String[this.getDescription().size()+1];
        for (int i = 0; i <this.getDescription().size() ; i++) {
            lore[i] = Formatter.color(this.getDescription().get(i));
        }

        lore[lore.length-1] = Formatter.color("&eCost: &7" + this.getPrice());

        return ItemBuilder.construct().create(Material.ARROW, 1)
                .setDisplay("&c&l" + this.getName())
                .setLore(lore).addEnchants(Enchantment.DURABILITY)
                .setEnchantLevels(1).addFlags(ItemFlag.values())
                .build();

    }

    @Override
    public void handleLaunch(ProjectileLaunchEvent e) {

        if(e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            if(arrow.getShooter() instanceof Player) {
                this.arrows.add(arrow);
                this.runEffect(arrow);
            }
        }
    }

    @Override
    public void handleHit(ProjectileHitEvent e) {
        if(e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow)e.getEntity();
            arrows.remove(arrow);
        }
    }

    @Override
    public void runEffect(Projectile entity) {

        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity instanceof Arrow) {

                    Arrow projectile = (Arrow) entity;
                    if (arrows.contains(projectile)) {

                        Location loc = projectile.getLocation();
                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(getParticle(),
                                true,
                                (float) loc.getX(),
                                (float) loc.getY(),
                                (float) loc.getZ(),
                                0,
                                0,
                                0,
                                1,
                                0);

                        Bukkit.getOnlinePlayers().forEach(p -> {
                            if (p.getWorld().equals(loc.getWorld()) && p.getLocation().distanceSquared(loc) <= 16 * 16) {
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                        });
                    }
                } else {
                    this.cancel();
                }

            }
        }.runTaskTimer(DmarPvP.getInstance(), 0, 1);
    }


    @Override
    public int getPrice() {
        return 10000;
    }


}
