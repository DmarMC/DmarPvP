package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.core.items.Excalibur;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.items.ProtectiveArmor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsManager {

    private final Map<String, SpecialItem> specialItemsMap;

    public ItemsManager() {

        this.specialItemsMap = new ConcurrentHashMap<>();
        addSI(new Excalibur());
        addSI(new ProtectiveArmor());
        //addSI(new StormBreaker());

    }

    public Map<String, SpecialItem> getSpecialItemsMap() {
        return specialItemsMap;
    }


    private void addSI(SpecialItem specialItem) {
        specialItemsMap.put(specialItem.getName(), specialItem);
    }

    public SpecialItem getItem(String name) {
        return specialItemsMap.getOrDefault(name, null);
    }


}
