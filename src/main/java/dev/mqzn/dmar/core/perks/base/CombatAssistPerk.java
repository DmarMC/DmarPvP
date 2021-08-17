package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.core.combat.Battle;
import dev.mqzn.dmar.core.events.PostCombatTriggerEvent;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.function.Consumer;

public abstract class CombatAssistPerk extends Perk{

    private final Consumer<Player> actions;

    public CombatAssistPerk() {
        super();
        this.actions = setActions();
    }

    public abstract Consumer<Player> setActions();

    public Consumer<Player> getActions() {
        return actions;
    }

    public abstract boolean getHealthCondition(Player player);

    public void onCombat(PostCombatTriggerEvent e) {

        @Nonnull
        Battle battle = e.getBattle();

        Player defender = battle.getDefender();
        if(getHealthCondition(defender)) {
            this.actions.accept(defender);
        }

    }


}
