package dev.mqzn.dmar.tasks;

import dev.mqzn.dmar.core.combat.Battle;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.managers.CombatManager;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatTask extends BukkitRunnable {

    private final CombatManager manager;

    {
        manager = DmarPvP.getInstance().getCombatManager();
    }

    private final Battle battle;
    public CombatTask(Battle battle) {
        this.battle = battle;
    }

    private int count = 15;
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        Player defender = battle.getDefender();
        Player attacker = battle.getAttacker();

        if((attacker == null || !attacker.isOnline()) || count < 0) {
            Formatter.sendMsg(defender, "&aYou are no longer in combat !");
            manager.removeCombat(defender.getUniqueId());

            if(attacker != null) {
                Formatter.sendMsg(attacker, "&aYou are no longer in combat !");
                manager.removeCombat(attacker.getUniqueId());
            }

            this.cancel();
            return;
        }

        defender.setLevel(count);
        attacker.setLevel(count);

        count--;
    }


}
