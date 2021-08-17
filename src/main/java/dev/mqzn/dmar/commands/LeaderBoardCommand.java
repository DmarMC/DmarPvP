package dev.mqzn.dmar.commands;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.util.Chat;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.LinkedHashSet;

public class LeaderBoardCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println("Only players can do this");
            return true;
        }

        // /leaderboards [page]
        Player executor = (Player) sender;

        LinkedHashSet<UserData> topUserData = DmarPvP.getInstance().getLeaderBoardTask().getLeaderBoards();

        if(!topUserData.isEmpty()) {

            String color;
            Formatter.sendMsg(executor, "&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==");
            Formatter.sendMsg(executor, Chat.centerMsg("&f&l>> &9&lLeaderBoard &f&l<<"));

            int o = 1;
            for(UserData data : topUserData) {

                color = o == 1 || o == 2 || o == 3 ? "c" : "e";

                Player player = Bukkit.getPlayer(data.getId());
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getId());

                String name = player == null ? offlinePlayer.getName() : player.getName();


                Formatter.sendMsg(executor, "&" + color + "#" + o +  " " + name);
                o++;
            }


            Formatter.sendMsg(executor, "&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==&7&l&m==&8&l&m==");

        }else {
            Formatter.sendMsg(executor, "&cThere are no users that joined the server");
        }
        return true;
    }


}
