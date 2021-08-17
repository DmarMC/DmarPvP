package dev.mqzn.dmar.core.managers;

import com.google.common.collect.Maps;
import dev.mqzn.dmar.core.combat.Battle;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.events.PostCombatTriggerEvent;
import dev.mqzn.dmar.util.Tasks;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.UUID;


public class CombatManager {

    private final Map<UUID, Battle> battlesMap;

    public CombatManager() {
        battlesMap = Maps.newConcurrentMap();
    }

    public boolean isInCombat(Player player) {
        if(player == null) return false;

        for(Battle battle : battlesMap.values()) {
            if(battle.getDefender().getUniqueId().equals(player.getUniqueId())
                    || battle.getAttacker().getUniqueId().equals(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public void setCombatBetween(Player attacker, Player defender) {

        removeCombat(attacker.getUniqueId());
        removeCombat(defender.getUniqueId());

        Battle battle = new Battle(attacker, defender);
        battlesMap.put(defender.getUniqueId(), battle);
        battle.getCombatTask().runTaskTimer(DmarPvP.getInstance(), 0, 20);

        Tasks.callEvent(new PostCombatTriggerEvent(battle));
    }

    public Player getLastAttackerOf(UUID victim) {

        Battle battle = battlesMap.get(victim);
        if(battle == null) return null;

        return battle.getAttacker();

    }

    public void removeCombat(UUID user) {
        Battle b = battlesMap.get(user);
        battlesMap.remove(user);

        battlesMap.forEach((id, battle) -> {
            if(battle != null) {
                if (battle.getDefender().getUniqueId().equals(user)) {
                    battlesMap.remove(id, battle);
                }
            }else {
                battlesMap.remove(id, null);
            }
        });


        if(b != null ) {
            b.getCombatTask().cancel();
        }

    }


}
