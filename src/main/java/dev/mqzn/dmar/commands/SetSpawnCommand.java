package dev.mqzn.dmar.commands;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println("Only players can do this !");
            return false;
        }

        Player player = (Player)sender;
        if(!player.hasPermission("pvp.setspawn")) {
            Formatter.sendMsg(player, Msg.NO_PERMISSION);
            return false;
        }

        if(args.length != 0) {
            Formatter.sendMsg(player, "&cUsage: /SetSpawn");
            return false;
        }

        DmarPvP.getInstance()
                .getUserDataManager().setSpawnLocation(player.getLocation());
        Formatter.sendMsg(player, "&aSpawn has been set !");

        return true;

    }


}
