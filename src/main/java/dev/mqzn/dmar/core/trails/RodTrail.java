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
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;

public abstract class RodTrail extends Trail{

    private final Map<Player, FishHook> hookes;

    public RodTrail() {
        hookes = new HashMap<>();
    }


    @Override
    public ItemStack getMenuItem() {
        String[] lore = new String[this.getDescription().size()+1];
        for (int i = 0; i <this.getDescription().size() ; i++) {
            lore[i] = Formatter.color(this.getDescription().get(i));
        }

        lore[lore.length-1] = Formatter.color("&eCost: &7" + this.getPrice());

        return ItemBuilder.construct().create(Material.FISHING_ROD, 1)
                .setDisplay(this.getDisplayName())
                .setLore(lore).addEnchants(Enchantment.LUCK)
                .setEnchantLevels(1).addFlags(ItemFlag.values()).build();

    }


    public void onHookBack(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        ItemStack itemInHand = e.getItem();

        if(itemInHand == null || itemInHand.getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (!hookes.containsKey(player)) { //NOT IN AIR
                return;
            }
            hookes.remove(player);
        }

    }


    @Override
    public void handleLaunch(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof FishHook) {
            FishHook hook = (FishHook)e.getEntity();
            if(hook.getShooter() instanceof Player) {
                Player player = (Player) hook.getShooter();
                hookes.put(player, hook);
                this.runEffect(hook);
            }
        }
    }




    @Override
    public void handleHit(ProjectileHitEvent e) {
        if(e.getEntity() instanceof FishHook) {
            FishHook fishHook = (FishHook)e.getEntity();
            if(fishHook.getShooter() instanceof Player) {
                Player shooter = (Player)fishHook.getShooter();
                this.hookes.remove(shooter);
            }
        }

    }

    //this is the main method here
    @Override
    public void runEffect(Projectile entity) {
        
        new BukkitRunnable() {
            @Override
            public void run() {
                
                if(entity instanceof FishHook) {
                    
                    FishHook hook = (FishHook)entity;
                    if(entity.getShooter() instanceof Player) {
                        Player shooter = (Player)entity.getShooter();
                        if (hookes.containsValue(hook) && hookes.containsKey(shooter)) {

                            Location loc = hook.getLocation();
                            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(getParticle(),
                                    true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 1, 0);

                            Bukkit.getOnlinePlayers().forEach(p -> {
                                if (p.getWorld().equals(loc.getWorld()) && p.getLocation().distanceSquared(loc) <= 16 * 16) {
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                }
                            });
                        } else {
                            this.cancel();
                        }
                    }
                }
                
            }
            
        }.runTaskTimer(DmarPvP.getInstance(), 0, 1);
        
    }

    @Override
    public int getPrice() {
        return 15000;
    }


}
