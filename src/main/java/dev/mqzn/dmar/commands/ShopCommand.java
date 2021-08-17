package dev.mqzn.dmar.commands;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.managers.CombatManager;
import dev.mqzn.dmar.core.managers.MenuManager;
import dev.mqzn.dmar.menus.ShopMenu;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player)sender;

        if(args.length != 0) {
            Formatter.sendMsg(player, "&cUsage: /Shop");
            return false;
        }

        CombatManager combatManager = DmarPvP.getInstance().getCombatManager();
        if(combatManager.isInCombat(player) && !player.isOp()) {
            Formatter.sendMsg(player, "&cYou cannot execute that command while in combat !");
            return false;
        }

        MenuManager.getInstance().openMenu(player, new ShopMenu());
        return true;

    }

}
