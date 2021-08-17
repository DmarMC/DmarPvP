package dev.mqzn.dmar;

import dev.mqzn.dmar.backend.SpawnYml;
import dev.mqzn.dmar.backend.db.SqlConnection;
import dev.mqzn.dmar.commands.*;
import dev.mqzn.dmar.core.board.BoardAdapter;
import dev.mqzn.dmar.core.board.api.Assemble;
import dev.mqzn.dmar.core.managers.*;
import dev.mqzn.dmar.core.perks.base.PerkSlotEdits;
import dev.mqzn.dmar.core.ranks.Rank;
import dev.mqzn.dmar.listeners.*;
import dev.mqzn.dmar.menus.kitsmenu.KitViewerManager;
import dev.mqzn.dmar.tasks.LeaderBoardTask;
import dev.mqzn.dmar.util.Logger;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DmarPvP extends JavaPlugin {

    private static DmarPvP instance;
    private Assemble assemble;
    private KitManager kitManager;
    private UserDataManager userDataManager;
    private PerkManager perkManager;
    private CombatManager combatManager;
    private RankManager rankManager;
    private TrailsManager trailsManager;
    private BattleCryManager battleCryManager;
    private KitViewerManager kitViewerManager;
    private ItemsManager itemsManager;
    private SpawnYml spawnYml;
    private SqlConnection sqlConnection;
    private LeaderBoardTask leaderBoardTask;
    private PerkSlotEdits perkSlotEdits;
    private boolean useLP;
    private static LuckPerms luckPerms;

    @Override
    public void onEnable() {

        super.onEnable();

        // Plugin startup logic
        instance = this;

        spawnYml = new SpawnYml();
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        Logger.defaultLog("&eLoading Managers...");
        kitManager = new KitManager();
        userDataManager = new UserDataManager();
        perkManager = new PerkManager();
        combatManager = new CombatManager();
        rankManager = new RankManager();
        kitViewerManager = new KitViewerManager();
        trailsManager = new TrailsManager();
        itemsManager = new ItemsManager();
        battleCryManager = new BattleCryManager();
        Logger.defaultLog("&aManagers have been loaded !");

        Logger.defaultLog("&eEstablishing a connection to the MySQL database");
        sqlConnection = new SqlConnection();
        sqlConnection.connect();
        Logger.defaultLog("&aConnection has been initialized, &eLoading data....");

        Logger.defaultLog("&bLoading Events and Commands");
        perkSlotEdits = new PerkSlotEdits();


        assemble = new Assemble(this, new BoardAdapter());
        useLP = Bukkit.getPluginManager().isPluginEnabled("LuckPerms");

        this.registerListeners();
        this.registerCommands();
        Logger.defaultLog("&6Menus, events and commands are loaded");


        if(useLP) {
            Logger.defaultLog("&aLuckPerms has been found, hooking with LuckPerms...");
        }else {
            Logger.defaultLog("&cCouldn't find Luckperms enabled on the server !");
        }


        Logger.logEnable();

        leaderBoardTask = new LeaderBoardTask();
        leaderBoardTask.updateLB();
        leaderBoardTask.runTaskTimerAsynchronously(this, 20, (leaderBoardTask.getPeriod()*20) );
    }

    private void registerCommands() {
        this.getCommand("shop").setExecutor(new ShopCommand());
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
        this.getCommand("leaderboard").setExecutor(new LeaderBoardCommand());
        this.getCommand("ranks").setExecutor(new RanksCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger.defaultLog("&cDisconnecting from MySQL database...");
        sqlConnection.disconnect();
        Logger.defaultLog("&eDisconnected from the database successfully");

        Logger.logDisable();
    }

    public static DmarPvP getInstance() {
        return instance;
    }

    public SpawnYml getSpawnYml() {
        return spawnYml;
    }

    public Assemble getAssemble() {
        return assemble;
    }

    public CombatManager getCombatManager() {
        return combatManager;
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }

    public TrailsManager getTrailsManager() {
        return trailsManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public UserDataManager getUserDataManager() {
        return userDataManager;
    }

    public PerkManager getPerkManager() {
        return perkManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public KitViewerManager getKitViewerManager() {
        return kitViewerManager;
    }


    public LeaderBoardTask getLeaderBoardTask() {
        return leaderBoardTask;
    }

    public PerkSlotEdits getPerkSlotEdits() {
        return perkSlotEdits;
    }

    public BattleCryManager getBattleCryManager() {
        return battleCryManager;
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ItemsInteractionListener(), this);
        pm.registerEvents(new CombatListener(), this);
        pm.registerEvents(new TrailsListener(), this);
        pm.registerEvents(new MenuListener(), this);
        pm.registerEvents(new RegisterListener(assemble), this);
    }

    public boolean useLP() {
        return useLP;
    }

    public SqlConnection getSqlConnection() {
        return sqlConnection;
    }


    public static LuckPerms getLuckPerms() {
        if(luckPerms == null) {
            luckPerms = LuckPermsProvider.get();
        }
        return luckPerms;
    }



}
