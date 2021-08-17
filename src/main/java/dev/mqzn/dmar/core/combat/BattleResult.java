package dev.mqzn.dmar.core.combat;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.CombatManager;
import dev.mqzn.dmar.core.managers.KitManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.util.ActionBar;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BattleResult {

    private final Player killer, victim;
    private final Date date;
    private final double health;
    private final KillMethod method;

    public BattleResult(Player killer, Player victim) {
        this.killer = killer;
        this.victim = victim;
        this.health = killer.getHealth();
        this.date = new Date();

        this.method = this.initKillMethod();

    }


    public KillMethod getMethod() {
        return method;
    }

    private KillMethod initKillMethod() {

        ItemStack item = killer.getItemInHand();
        if(item == null || item.getType() == Material.AIR) {
            return KillMethod.HANDS;
        }

        if(item.getType().name().endsWith("SWORD")) {
            return KillMethod.SWORD;
        }

        switch (item.getType()) {
            case FISHING_ROD:
                return KillMethod.ROD;
            case BOW:
                return KillMethod.BOW;
            default: {
                EntityDamageEvent e = victim.getLastDamageCause();
                if(e == null) return KillMethod.UNKNOWN;
                if(e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE) {
                    return KillMethod.FIRE_CHARGES;
                }

                return KillMethod.UNKNOWN;

            }
        }

    }

    public Date getDate() {
        return date;
    }

    public Player getKiller() {
        return killer;
    }

    public Player getVictim() {
        return victim;
    }

    public int getKillerPoints(UserData victim) {
        double randomPercent = ThreadLocalRandom.current().nextDouble(0.03, 0.05);
        return (int) (randomPercent*victim.getPoints());
    }

    public void sendMessages(KillMethod method) {

        String health = Formatter.formatDouble(getHealth(), 1);

        Formatter.sendMsg(killer, "&7You have killed &e" + victim.getName()
                + " &7with &c" + health + " HP remaining &7Using your " + method.getMessage());

        Formatter.sendMsg(victim, "&e" + killer.getName() + " &7destroyed you With &c"
                + health + " HP remaining &7Using his " + method.getMessage());
    }

    public void process(CombatManager combatManager, UserDataManager userDataManager, KitManager kitManager, UserData victimRecent, UserData killerRecent) {
        int reward = this.getKillerPoints(victimRecent);

        userDataManager.addDeaths(victimRecent, 1);

        if(victimRecent.getPoints()-reward >= 0) {
            userDataManager.addPoints(victimRecent, -reward);
        }else {
            victimRecent.setPoints(0);
        }

        if(victimRecent.getKillStreak() > victimRecent.getHighestKillStreak()) {
            userDataManager.setHighestKillStreak(victimRecent, victimRecent.getKillStreak());
        }

        victimRecent.setKillStreak(0);
        combatManager.removeCombat(victim.getUniqueId());

        int coins = ThreadLocalRandom.current().nextInt(35);

        userDataManager.addKills(killerRecent, 1);
        kitManager.addFireCount(killer, 1);
        userDataManager.addPoints(killerRecent, reward);
        userDataManager.addCoins(killerRecent, coins);
        killerRecent.setKillStreak(killerRecent.getKillStreak()+1);

        new ActionBar().sendActionBar(killer, "&ePoints: &a+&7" + reward
                + "&f, &6Coins: &a+&7" + coins);

        if(killerRecent.getKillStreak() % 3 == 0) {
            Bukkit.broadcastMessage(Formatter.color(Formatter.PREFIX + " &6" +
                    this.getKiller().getName() + " &eis Now at a killstreak of " +
                    killerRecent.getKillStreak()));
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BattleResult)) return false;
        BattleResult that = (BattleResult) o;
        return Objects.equals(getKiller(), that.getKiller()) &&
                Objects.equals(getVictim(), that.getVictim()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKiller(), getVictim(), getDate());
    }


    public double getHealth() {
        return health;
    }


}
