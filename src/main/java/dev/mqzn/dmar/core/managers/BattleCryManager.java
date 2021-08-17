package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import dev.mqzn.dmar.core.battlecry.battlecries.Achievement;
import dev.mqzn.dmar.core.battlecry.battlecries.DonkeyDeath;
import dev.mqzn.dmar.core.battlecry.battlecries.FireworkWhizz;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BattleCryManager {

    private final Map<String, BaseBattleCry> battleCryMap;

    public BattleCryManager() {
        this.battleCryMap = new ConcurrentHashMap<>();
        this.addBC(new DonkeyDeath());
        this.addBC(new FireworkWhizz());
        this.addBC(new Achievement());
    }

    private void addBC(BaseBattleCry baseBattleCry) {
        this.battleCryMap.put(baseBattleCry.getName(), baseBattleCry);
    }

    public BaseBattleCry getBattleCry(String name) {
        return this.battleCryMap.getOrDefault(name, null);
    }

    public Map<String, BaseBattleCry> getBattleCryMap() {
        return battleCryMap;
    }

}
