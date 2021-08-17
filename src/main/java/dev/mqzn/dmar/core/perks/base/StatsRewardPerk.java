package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.events.StatsChangeEvent;
import dev.mqzn.dmar.core.managers.UserDataManager;
import org.bukkit.entity.Player;
import java.util.function.BiConsumer;

public abstract class StatsRewardPerk extends Perk {

    private final BiConsumer<UserDataManager, Player> rewardActions;

    public StatsRewardPerk() {
        super();
        rewardActions = this.getRewardActions();
    }

    public abstract boolean getRewardLogic(UserData recent);

    public abstract BiConsumer<UserDataManager, Player> getRewardActions();

    public void onStatsChange(StatsChangeEvent e, UserDataManager dataManager) {

        UserData recent = e.getNewStats();
        Player player = e.getPlayer();

        if(this.getRewardLogic(recent)) {
            this.rewardActions.accept(dataManager, player);
        }

    }

}
