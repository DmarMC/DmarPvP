package dev.mqzn.dmar.commands;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println("Only players can do this");
            return true;
        }

        Player target;
        Player executor = (Player)sender;

        if(args.length == 0){
            target = executor;
        }else if(args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
        }else {
            Formatter.sendMsg(executor, "&cUsage: /" + label.toLowerCase() + " <player>");
            return true;
        }

        if(target == null || !target.isOnline()) {
            Formatter.sendMsg(executor, Msg.UNFOUND_PLAYER);
            return true;
        }

        UserData targetData = DmarPvP.getInstance().getUserDataManager().getData(target.getUniqueId());

        StringBuilder perks = new StringBuilder();
        int size = targetData.getSelectedPerks().size();

        if(size > 0) {
            int i = 0;
            for (Map.Entry<Integer, Perk> entry : targetData.getSelectedPerks().entrySet()) {
                Perk perk = entry.getValue();
                perks.append(perk.getDisplayName());
                if (i != 2) {
                    perks.append("&7,");
                }
                i++;
            }
        }else {
            perks.append("&cNONE");
        }


        for(String msg : DmarPvP.getInstance().getConfig().getStringList("Messages.Stats")) {
            msg = msg.replace("<name>", target.getName())
                    .replace("<rank>", targetData.getRank().getName())
                    .replace("<kit>", targetData.getKit().getDisplayName())
                    .replace("<battle_cry>", targetData.getBattleCry() == null ? "&cNONE" : targetData.getBattleCry().getDisplayName())
                    .replace("<kills>", "" + targetData.getKills())
                    .replace("<deaths>", "" + targetData.getDeaths())
                    .replace("<coins>", "" + targetData.getCoins())
                    .replace("<points>", "" + targetData.getPoints())
                    .replace("<highest_killstreak>", "" + targetData.getHighestKillStreak())
                    .replace("<perks>", perks.toString());

            Formatter.sendMsg(executor, msg);
        }

        return true;
    }

}
