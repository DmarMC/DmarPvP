package dev.mqzn.dmar.core.board;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.board.api.AssembleAdapter;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return Formatter.color("&8▄▆▛ &4&lDmar&c&lPvP &8▜▆▄");
    }

    @Override
    public List<String> getLines(Player player) {


        UserData userData = DmarPvP.getInstance().getUserDataManager().getData(player.getUniqueId());

        List<String> lines = new ArrayList<>();
        lines.add("&7&l&m+---------------+");
        lines.add("&e&lAccount");
        lines.add("&8» &7" + player.getName());
        lines.add("");
        lines.add("&9&lRank");
        lines.add("&8» " + userData.getRank().getDisplayName());
        lines.add("");
        lines.add("&a&lPoints");
        lines.add("&8» &7" + userData.getPoints());
        lines.add("");
        lines.add("&2&lKillStreaks");
        lines.add("&8» &7" + userData.getKillStreak());
        lines.add("");
        lines.add("&6IP: &eDmarPvP.net");
        lines.add("&7&l&m+---------------+");
        return Formatter.colorList(lines);
    }


}
