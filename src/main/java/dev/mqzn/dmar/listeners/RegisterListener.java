package dev.mqzn.dmar.listeners;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.board.api.Assemble;
import dev.mqzn.dmar.core.board.api.AssembleBoard;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RegisterListener implements Listener {

    private final Assemble assemble;
    private final UserDataManager dataManager = DmarPvP.getInstance().getUserDataManager();
    public RegisterListener(Assemble assemble) {
        this.assemble = assemble;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        e.setJoinMessage(Formatter.color("&8[&2+&8] &a" + player.getName()));

            if(player.isOp() && dataManager.getSpawn() == null) {
                Formatter.sendMsg(player, "&eSpawn is not SET !");
                Formatter.sendMsg(player, "&cExecute '/SetSpawn' to set the spawn");
            }

            if(dataManager.getSpawn() != null) {
                player.teleport(dataManager.getSpawn());
            }else {
                player.teleport(player.getWorld().getSpawnLocation());
            }


        UserData data = dataManager.loadFromID(DmarPvP.getInstance().getSqlConnection(), player);

        Kit kit = data.getKit();
        if (kit == null) {
            kit = DmarPvP.getInstance().getKitManager().getKitToApply(player);
        }
        kit.apply(player);
        data.setRank(DmarPvP.getInstance().getRankManager().calculateRank(data));
        data.setKit(kit);

        data.getSpecialItems().forEach( (sp, t) -> {
            if((System.currentTimeMillis()-t) < UserData.ITEMS_TIME_IN_MS) {
                sp.apply(player);
            }else {
                player.sendMessage(Formatter.color(sp.getDisplayName() + "'s &7Period has &cEXPIRED"));
                data.getSpecialItems().remove(sp);
            }
        });

        assemble.getBoards().put(player.getUniqueId(), new AssembleBoard(player, assemble));
        dataManager.getPlayerDataMap().put(player.getUniqueId(), data);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setLevel(0);

    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        e.setQuitMessage(Formatter.color("&8[&4-&8] &c" + player.getName()));

        assemble.getBoards().remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        dataManager.getPlayerDataMap().remove(player.getUniqueId());

    }

}
