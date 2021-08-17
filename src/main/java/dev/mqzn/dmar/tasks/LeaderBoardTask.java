package dev.mqzn.dmar.tasks;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.backend.db.SqlConnection;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.managers.TrailsManager;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Conversions;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LeaderBoardTask extends BukkitRunnable {

    private final long period = TimeUnit.MINUTES.toSeconds(1);
    private final SqlConnection sql = DmarPvP.getInstance().getSqlConnection();
    private long count;
    private final TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();
    private final LinkedHashSet<UserData> leaderBoards;
    public LeaderBoardTask() {
        leaderBoards = new LinkedHashSet<>();
        count = 0L;
    }


    @Override
    public void run() {


        if(count >= 10) {
            this.updateLB();
            broadcast("&aLeaderboard has been updated !");
            count = 0;
            return;
        }

        if(count == 5) {
            broadcast("&6Leaderboards &7will be updated in &e5 minutes");
        }

        count++;
    }

    public long getPeriod() {
        return period;
    }

    public LinkedHashSet<UserData> getLeaderBoards() {
        return leaderBoards;
    }

    private void broadcast(String msg) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Formatter.sendMsg(player, msg);
        }
    }

    public void updateLB() {

        leaderBoards.clear();

        try (PreparedStatement st = sql.prepareStatement("SELECT * FROM `PlayersData` ORDER BY `POINTS` DESC LIMIT 10")){

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UUID id = Conversions.uuidFromBytes(rs.getBytes("UUID"));
                int kills = rs.getInt("Kills");
                int deaths = rs.getInt("Deaths");
                int highestKS = rs.getInt("HighestKS");
                int coins = rs.getInt("Coins");
                int points = rs.getInt("Points");
                String perks = rs.getString("SelectedPerks");

                String bc = rs.getString("BattleCry");
                BaseBattleCry battleCry = bc == null || bc.isEmpty() ? null : DmarPvP.getInstance().getBattleCryManager().getBattleCry(bc);

                String rt = rs.getString("RodTrail");
                String at = rs.getString("ArrowTrail");
                RodTrail rodTrail = rt == null || rt.isEmpty() ? null : (RodTrail) trailsManager.getTrail(rt);
                ArrowsTrail arrowsTrail = rt == null || rt.isEmpty() ? null : (ArrowsTrail)trailsManager.getTrail(at);

                String items = rs.getString("SpecialItems");

                Map<SpecialItem, Long> specialItems;
                if(items.equals("NULL") || items.isEmpty()) {
                    specialItems = new ConcurrentHashMap<>();
                }else {
                    specialItems = Conversions.deserializeItemsMap(items);
                }

                Map<Integer, Perk> perkMap;
                if(perks.equals("NULL") ||perks.isEmpty()) {
                    perkMap = new ConcurrentHashMap<>();
                }else {
                    perkMap = Conversions.deserializePerksMap(perks);
                }

                leaderBoards.add(new UserData(id, points, coins, kills, deaths,
                        highestKS, battleCry, rodTrail, arrowsTrail, perkMap, specialItems));

            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
