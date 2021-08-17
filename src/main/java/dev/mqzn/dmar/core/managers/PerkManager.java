package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.perks.GoldenHeadPerk;
import dev.mqzn.dmar.core.perks.MurderPerk;
import dev.mqzn.dmar.core.perks.SniperPerk;
import dev.mqzn.dmar.core.perks.base.*;
import dev.mqzn.dmar.exceptions.PerkSlotOutOfBounds;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PerkManager {

    private final Map<String, Perk> perks;

    public PerkManager() {

        perks = new HashMap<>();

        perks.put("murder", new MurderPerk());
        perks.put("sniper", new SniperPerk());
        perks.put("goldenhead", new GoldenHeadPerk());

    }



    public Perk getPerk(String perkName) {
        return perks.get(perkName);
    }

    public Map<String, Perk> getPerks() {
        return perks;
    }

    public void setPerkAt(UserData userData, Perk perk, int slot) throws PerkSlotOutOfBounds {
        if(slot > 3) {
            throw new PerkSlotOutOfBounds(perk, slot);
        }

        Map<Integer, Perk> selected = userData.getSelectedPerks();
        selected.put(slot, perk);
        userData.setSelectedPerks(selected);
    }

    /*
    Returns the slot where the perk will be added !
     */

    public int addPerk(UserDataManager dataManager, Player player, Perk perk) {
        UserData userData = dataManager.getData(player.getUniqueId());
        Map<Integer, Perk> selectedPerks = userData.getSelectedPerks();

        if(selectedPerks.size() == 3) {
            Formatter.sendMsg(player, "&cYou can't have more than 3 perks !");
            return -1;
        }

        if(selectedPerks.containsValue(perk)) {
            player.closeInventory();
            Formatter.sendMsg(player, "&cYou already have that perk in another slot !");
            return -1;
        }

        if(selectedPerks.size() < 3 && !selectedPerks.isEmpty()) {
            for (int s = 0, slot = s+1; s < 3; s++, slot = s+1) {
                if(userData.getPerkAt(slot) != null) continue;

                try {
                    this.setPerkAt(userData, perk, slot);
                    return slot;
                } catch (PerkSlotOutOfBounds ex) {
                    player.closeInventory();
                    Formatter.sendMsg(player, "&cYou can't have more than 3 perks !");
                    break;
                }
            }

        }

        return -1;
    }

    public boolean hasPerk(UserData data) {
        return !data.getSelectedPerks().isEmpty();
    }

    public boolean hasPerk(UserData data, Perk perk) {
        return slotOfPerk(data, perk.getClass()) != -1;
    }

    public <P extends Perk> boolean hasPerk(UserData data, Class<P> clazz) {

        for(Perk perk : data.getSelectedPerks().values()) {
            if(clazz.isAssignableFrom(perk.getClass())) {
                return true;
            }
        }
        return false;
    }

    public <P extends Perk> int slotOfPerk(UserData data, Class<P> clazz) {

        for(Map.Entry<Integer, Perk> perkEntry : data.getSelectedPerks().entrySet()) {
            if(clazz.isAssignableFrom(perkEntry.getValue().getClass())) {
                return perkEntry.getKey();
            }
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
    public <P extends Perk> Set<P> getPerksOfType(UserData data, Class<P> clazz) {

        Set<P> set = new HashSet<>();

        if(data.getSelectedPerks().isEmpty()) return set;
        for(Perk p : data.getSelectedPerks().values()) {

            if(clazz.isAssignableFrom(p.getClass())) {
                set.add((P)p);
            }
        }

        return set;
    }



}
