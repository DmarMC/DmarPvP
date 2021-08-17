package dev.mqzn.dmar.core.perks;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.perks.base.StatsRewardPerk;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.function.BiConsumer;

public class SniperPerk extends StatsRewardPerk {

    @Override
    public boolean getRewardLogic(UserData recent) {
        return recent.getKillStreak() % 5 == 0;
    }

    @Override
    public BiConsumer<UserDataManager, Player> getRewardActions() {
        return ((userDataManager, player) -> {

            UserData userData = userDataManager.getData(player.getUniqueId());
            Kit kit = userData.getKit();

            player.getInventory().addItem(ItemBuilder.construct().create(Material.ARROW, 2)
                    .addFlags(ItemFlag.values()).setDisplay(kit.getDisplayName())
                    .setUnbreakable(true).build());

            Formatter.sendMsg(player, "&7You have received &a+2 arrows from " + this.getDisplayName());
        });
    }

    @Override
    public String getName() {
        return "sniper";
    }

    @Override
    public String getDisplayName() {
        return "&3Sniper Perk";
    }

    @Override
    public String getDescription() {
        return "&7You get 2 arrows on each 5 kills";
    }

    @Override
    public Material getIcon() {
        return Material.BOW;
    }

    @Override
    public int getSlot() {
        return 1;
    }

    @Override
    public int getPrice() {
        return 30000;
    }

}
