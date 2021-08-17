package dev.mqzn.dmar.listeners;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.combat.Battle;
import dev.mqzn.dmar.core.events.PostCombatTriggerEvent;
import dev.mqzn.dmar.core.managers.*;
import dev.mqzn.dmar.core.perks.GoldenHeadPerk;
import dev.mqzn.dmar.core.perks.base.CombatAssistPerk;
import dev.mqzn.dmar.core.ranks.Rank;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.combat.BattleResult;
import dev.mqzn.dmar.core.events.StatsChangeEvent;
import dev.mqzn.dmar.core.perks.base.StatsRewardPerk;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import java.util.Objects;

public class CombatListener implements Listener {

    private final CombatManager combatManager;
    private final UserDataManager userDataManager;
    private final RankManager rankManager;
    private final KitManager kitManager;
    private final PerkManager perkManager;

    {
        combatManager = DmarPvP.getInstance().getCombatManager();
        userDataManager = DmarPvP.getInstance().getUserDataManager();
        rankManager = DmarPvP.getInstance().getRankManager();
        kitManager = DmarPvP.getInstance().getKitManager();
        perkManager = DmarPvP.getInstance().getPerkManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.getDrops().clear();
        e.setNewExp(0);
        e.setNewLevel(0);
        e.setDroppedExp(0);
        userDataManager.respawn(e.getEntity());
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent e) {

        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player)e.getEntity();

        if(e.getCause() == DamageCause.FALL
                || e.getCause() == DamageCause.DROWNING) {
            e.setCancelled(true);
        }

        boolean b = e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE
                || e.getCause() == DamageCause.LAVA;

        if(b && e.getCause() != DamageCause.ENTITY_ATTACK && e.getFinalDamage() > player.getHealth() ) {

            Player killer = combatManager.getLastAttackerOf(player.getUniqueId());
            this.deathProcess(player, killer);
        }

    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e) {

        if(!(e.getEntity() instanceof Player)) return;
        Player victim = (Player)e.getEntity();

        Player killer = null;
        if(e.getDamager() instanceof Player) {
            killer = (Player) e.getDamager();
        }else if(e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile)e.getDamager();
            if(!(projectile.getShooter() instanceof Player)) return;
            killer = (Player)projectile.getShooter();
        }

        if(killer == null) return;

        double dmg = e.getFinalDamage();

        boolean b = e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE
                || e.getCause() == DamageCause.LAVA;

        if(!b && dmg < victim.getHealth()) {
            combatManager.setCombatBetween(killer, victim);
            return;
        }


        if((e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.PROJECTILE) && dmg >= victim.getHealth()) {
            e.setCancelled(true);
            this.deathProcess(victim, killer);
        }

    }

    private void deathProcess(Player victim, Player killer) {

        if(killer == null) {
            return;
        }

        UserData victimRecent = userDataManager.getData(victim.getUniqueId());

        UserData killerRecent;
        if(userDataManager.getData(killer.getUniqueId()) == null) {
            return;
        }
        killerRecent = userDataManager.getData(killer.getUniqueId());

        BattleResult battleResult = new BattleResult(killer, victim);
        battleResult.process(combatManager, userDataManager, kitManager,  victimRecent, killerRecent);

        StatsChangeEvent killerStats = new StatsChangeEvent(killer, battleResult, killerRecent),
                victimStats = new StatsChangeEvent(victim, battleResult, victimRecent);

        Bukkit.getPluginManager().callEvent(killerStats);
        Bukkit.getPluginManager().callEvent(victimStats);

        battleResult.sendMessages(battleResult.getMethod());

        userDataManager.heal(killer);


        if(perkManager.hasPerk(killerRecent)
                && perkManager.hasPerk(killerRecent, GoldenHeadPerk.class)) {

            GoldenHeadPerk goldenHeadPerk = (GoldenHeadPerk) killerRecent.getPerkAt(perkManager
                    .slotOfPerk(killerRecent, GoldenHeadPerk.class));

            goldenHeadPerk.giveHead(killer);
        }

        if(killerRecent.getBattleCry() != null) {
            killerRecent.getBattleCry().execute(killer);
        }

        userDataManager.respawn(victim);

        Bukkit.getScheduler().runTaskLater(DmarPvP.getInstance(), ()-> {
            userDataManager.fullUpdate(victimRecent);
            userDataManager.fullUpdate(killerRecent);
        }, 2L);

    }

    @EventHandler
    public void onRod(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        if(e.getEntityType() == EntityType.FISHING_HOOK) {
            projectile.setVelocity(projectile.getVelocity().multiply(1.4));
        }
    }


    @EventHandler
    public void onArrowPick(PlayerPickupItemEvent e) {
        if(e.getItem().getItemStack().getType() == Material.ARROW) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFlow(BlockFromToEvent e) {
        if(e.getBlock().getType() == Material.STATIONARY_LAVA || e.getBlock().getType() == Material.LAVA) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLavaSpread(BlockSpreadEvent e) {
        if(e.getSource().getType() == Material.LAVA || e.getSource().getType() == Material.STATIONARY_LAVA) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onStatsChange(StatsChangeEvent e) {

        UserData current = e.getNewStats();


        if(!perkManager.hasPerk(current)) return;

        for(StatsRewardPerk rewardPerk : perkManager.getPerksOfType(current, StatsRewardPerk.class)) {
            rewardPerk.onStatsChange(e, userDataManager);
        }

        Rank oldRank, newRank;
        oldRank = current.getRank();
        newRank = rankManager.calculateRank(current);

        if(oldRank.equals(newRank)) return;

        current.setRank(newRank);
        userDataManager.getPlayerDataMap().replace(current.getId(), current);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();
        UserData data = userDataManager.getData(player.getUniqueId());

        String display;
        if(!DmarPvP.getInstance().useLP()) {
            display = "";
        }else {
            LuckPerms perms = DmarPvP.getLuckPerms();
             display = Objects.requireNonNull(perms.getGroupManager()
                    .getGroup(Objects.requireNonNull(perms.getUserManager()
                            .getUser(player.getUniqueId())).getPrimaryGroup()))
                     .getCachedData().getMetaData().getSuffix();
        }

        String msg = e.getMessage().replace("%", "%%");

        e.setFormat(Formatter.color(data.getRank().getDisplayName() +
                " " + display + player.getName() + " &8» &7" + msg));

    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void onCombatTrigger(PostCombatTriggerEvent e) {

        Battle battle = e.getBattle();

        UserData userData = userDataManager.getData(battle.getDefender().getUniqueId());
        if(!perkManager.hasPerk(userData)) return;

        perkManager.getPerksOfType(userData,
                CombatAssistPerk.class).forEach(cap -> cap.onCombat(e));

    }


}
