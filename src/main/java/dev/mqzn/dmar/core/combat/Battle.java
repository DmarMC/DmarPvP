package dev.mqzn.dmar.core.combat;

import dev.mqzn.dmar.tasks.CombatTask;
import org.bukkit.entity.Player;
import java.util.Objects;

public class Battle {

    private final Player attacker, defender;
    private final CombatTask combatTask;

    public Battle(Player attacker, Player defender) {
        this.attacker = attacker;
        this.defender = defender;
        combatTask = new CombatTask(this);
    }


    public Player getAttacker() {
        return attacker;
    }

    public CombatTask getCombatTask() {
        return combatTask;
    }

    public Player getDefender() {
        return defender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Battle)) return false;
        Battle battle = (Battle) o;
        return Objects.equals(getAttacker(), battle.getAttacker()) &&
                Objects.equals(getDefender(), battle.getDefender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttacker(), getDefender());
    }


}
