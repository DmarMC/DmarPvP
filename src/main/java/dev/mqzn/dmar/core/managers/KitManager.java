package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.core.kits.EmeraldKit;
import dev.mqzn.dmar.core.kits.PremiumKit;
import dev.mqzn.dmar.core.kits.VipKit;
import dev.mqzn.dmar.core.kits.base.FireItem;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.kits.MemberKit;
import dev.mqzn.dmar.core.kits.base.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class KitManager {

    private final Kit[] kits;
    private final Map<UUID, FireItem> fireCounterMap;

    public KitManager() {
        kits = new Kit[4];

        kits[0] = new EmeraldKit();
        kits[1] = new PremiumKit();
        kits[2] = new VipKit();
        kits[3] = new MemberKit();

        fireCounterMap = new ConcurrentHashMap<>();
    }

    public void setFireCount(Player player, int fireCount) {
        UserData data = DmarPvP.getInstance().getUserDataManager().getData(player.getUniqueId());
        int slot = player.getInventory().first(Material.FLINT_AND_STEEL);

        if(slot <0 || slot >= player.getInventory().getSize()) slot = 9;

        FireItem item = getFireItem(player.getUniqueId())  == null ? new FireItem(data.getKit()) : getFireItem(player.getUniqueId());
        item.setInitialCount(fireCount); //just in case
        fireCounterMap.put(player.getUniqueId(), item);
        item.updateFor(player, fireCount, slot);
    }

    public FireItem getFireItem(UUID id) {
        return fireCounterMap.get(id);
    }

    public Map<UUID, FireItem> getFireCounterMap() {
        return fireCounterMap;
    }

    public void addFireCount(Player player, int count) {
        if(player == null) return;
        int current = fireCounterMap.get(player.getUniqueId()) == null ? 1 : fireCounterMap.get(player.getUniqueId()).getCount();
        this.setFireCount(player, count+current);
    }

    public Kit getKitToApply(Player player) {

        for (Kit kit : kits) {
            if (player.hasPermission(kit.getPermission())) {
                return kit;
            }
        }

        return this.getDefaultKit();
    }


    public Kit getDefaultKit() {
        return kits[3];
    }

    public Kit[] getKits() {
        return kits;
    }


}
