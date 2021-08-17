package dev.mqzn.dmar.commands;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.managers.RankManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.ranks.Rank;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class RanksCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player)) {
            Logger.log("Only Players can do this", Logger.LogType.ERROR);
            return false;
        }

        Player player = (Player) commandSender;

        if(args.length > 0 || (!label.equalsIgnoreCase("ranks") &&
                !label.equalsIgnoreCase("ps") && !label.equalsIgnoreCase("prestige"))) {
            Formatter.sendMsg(player, "&cUsage: /Ranks");
            return false;
        }

        UserDataManager dataManager = DmarPvP.getInstance().getUserDataManager();
        UserData data = dataManager.getData(player.getUniqueId());

        RankManager rankManager = DmarPvP.getInstance().getRankManager();
        Map<Integer, Rank> ranks = rankManager.getRanks();

        Rank current = data.getRank();

        ranks.forEach((i, r) ->
                Formatter.sendMsg(player, r.getDisplayName()
                        + "&8--> &7" + r.getStartPoints()));

        int index = rankManager.indexOfRank(current);
        Formatter.sendMsg(player, "&eNext Rank: " + (index >= ranks.size()-1 ? "&cN/A" :
                rankManager.getRankAt(index+1).getDisplayName()));

        return true;
    }


}
