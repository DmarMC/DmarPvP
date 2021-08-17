package dev.mqzn.dmar.core.managers;

import com.google.common.collect.Maps;
import dev.mqzn.dmar.backend.db.DataUpdate;
import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Conversions;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.backend.SpawnYml;
import dev.mqzn.dmar.backend.db.SqlConnection;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.Logger;
import dev.mqzn.dmar.util.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataManager {

    private final Map<UUID, UserData> playerDataMap;
    private Location spawn;

    public UserDataManager() {
        playerDataMap = Maps.newConcurrentMap();
        spawn = this.loadSpawn();
    }

    public Map<UUID, UserData> getPlayerDataMap() {
        return playerDataMap;
    }

    private Location loadSpawn() {

        Location loc;
        try {
            SpawnYml yml = DmarPvP.getInstance().getSpawnYml();
            FileConfiguration conf = yml.getConfig();

            String path = "Spawn.";

            World world = Bukkit.getWorld(conf.getString(path + "world"));
            double x = conf.getDouble(path + "x");
            double y = conf.getDouble(path + "y");
            double z = conf.getDouble(path + "z");

            loc = new Location(world, x, y ,z);
        }catch (Throwable ex) {
            loc = null;
            Logger.log("&cCouldn't read the spawn location from spawn.yml, Spawn may not be set !", Logger.LogType.ERROR);
        }

        return loc;

    }

    public void setSpawnLocation(Location location) {

        if(location == null) return;
        spawn = location;

        Tasks.runAsync(()-> {
            SpawnYml yml = DmarPvP.getInstance().getSpawnYml();
            FileConfiguration conf = yml.getConfig();

            String p = "Spawn.";
            conf.set(p + "world", location.getWorld().getName());
            conf.set(p + "x", location.getX());
            conf.set(p + "y", location.getY());
            conf.set(p + "z", location.getZ());

            yml.save();
        });

    }

    public UserData getData(UUID id) {
        return playerDataMap.get(id);
    }


    public void addKills(UserData data, int kills) {

        data.setKills(data.getKills()+kills);
    }
    public void addDeaths(UserData data, int deaths) {
        data.setDeaths(data.getDeaths()+deaths);
    }

    public void addCoins(UserData data, int coins) {
        data.setCoins(data.getCoins()+coins);
    }

    public void addPoints(UserData data, int points) {
        data.setPoints(data.getPoints()+points);
    }

    public void setHighestKillStreak(UserData data, int highestKillStreak) {
        data.setHighestKillStreak(highestKillStreak);
    }

    public void fullUpdate(UserData data) {
        this.addData(data.getId(), data);
        DmarPvP.getInstance().getSqlConnection().updateData(data);
    }


    public void updateCached(UserData mutated) {
        this.playerDataMap.replace(mutated.getId(), mutated);
    }


    public Location getSpawn() {
        return spawn;
    }

    public void addData(UUID id, UserData data) {
        if(playerDataMap.get(id)==null) {
            this.playerDataMap.put(id, data);
        }else {
            this.playerDataMap.replace(id, data);
        }

    }

    public void respawn(Player player) {
        if(player == null) return;
        heal(player);
        player.setLevel(0);

        if(spawn != null) {
            player.teleport(spawn);
        }else {
            player.teleport(player.getWorld().getSpawnLocation());
        }

        KitManager manager = DmarPvP.getInstance().getKitManager();
        Kit kit = manager.getKitToApply(player);

        UserData data = this.getData(player.getUniqueId());


        Bukkit.getScheduler().runTaskLater(DmarPvP.getInstance(), ()-> {
            data.setKit(kit);
            kit.apply(player);

            data.getSpecialItems().forEach( (sp, t) -> {
                if((System.currentTimeMillis()-t) < UserData.ITEMS_TIME_IN_MS) {
                    sp.apply(player);
                }else {
                    player.sendMessage(Formatter.color(sp.getDisplayName() + "'s &7Period has &cEXPIRED"));
                    data.getSpecialItems().remove(sp);
                }
            });

            this.updateCached(data);

        }, 1L);



    }

    public void heal(@Nonnull Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }

    public synchronized UserData loadFromID(SqlConnection sql, Player player)  {

        UUID id = player.getUniqueId();

        KitManager kitManager = DmarPvP.getInstance().getKitManager();
        TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();

        try (PreparedStatement st = sql.prepareStatement("SELECT * FROM PlayersData WHERE UUID=?")) {

            st.setBytes(1, Conversions.uuidToBytes(id));

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                UserData d = new UserData(id);
                DmarPvP.getInstance().getSqlConnection().insertData(d);
                return d;
            }

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

            UserData data = new UserData(id, points, coins, kills, deaths,
                    highestKS, battleCry, rodTrail, arrowsTrail,
                    perkMap,
                    specialItems);

            data.setKit(kitManager.getKitToApply(player));
            data.setRank(DmarPvP.getInstance()
                    .getRankManager().calculateRank(data));

            return data;

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public void update(UserData userData, DataUpdate selectedPerks) {
        this.addData(userData.getId(), userData);
        DmarPvP.getInstance().getSqlConnection().updateData(userData, selectedPerks);
    }


}
