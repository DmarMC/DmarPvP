package dev.mqzn.dmar.util;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.managers.ItemsManager;
import dev.mqzn.dmar.core.perks.base.Perk;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Conversions {

    public static UUID uuidFromBytes(byte[] bytes) {

        if(bytes.length < 2) {
            throw new IllegalArgumentException("byte array is too small !") ;
        }

        final ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static byte[] uuidToBytes(UUID uuid) {
        return ByteBuffer.allocate(16).putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits()).array();
    }

    public static String perkToString(int slot, Perk selectedPerk) {
        return selectedPerk.getName() + "#" + slot;
    }



    public static String serializePerksMap(Map<Integer, Perk> map) {

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Map.Entry<Integer, Perk> entry : map.entrySet()) {
            builder.append(Conversions.perkToString(entry.getKey(), entry.getValue()));
            if(i != map.size()-1) {
                builder.append(",");
            }
            i++;
        }

        return builder.toString();
    }

    public static Map<Integer, Perk> deserializePerksMap(String str) {

        ConcurrentHashMap<Integer, Perk> map = new ConcurrentHashMap<>();

        String[] split = str.split(",");

        if(split.length == 0) return map;

        for (String s : split) {
            Cache<Integer, Perk> cache = Conversions.perkFromString(s);
            if(cache != null) {
                map.put(cache.getKey(), cache.getValue());
            }
        }

        return map;
    }

    private static Cache<Integer, Perk> perkFromString(String selectedPerkStr) {
        if(selectedPerkStr.indexOf('#') == -1) {
            //doesnt exist !
            return null;
        }

        String[] split = selectedPerkStr.split("#");
        String perkName = split[0];
        Perk perk = DmarPvP.getInstance().getPerkManager().getPerk(perkName);
        int slot = Integer.parseInt(split[1]);

        return new Cache<>(slot, perk);

    }



    private static String specialItemToStr(Cache<SpecialItem, Long> cached) {
        return cached.getKey().getName().toLowerCase() + ":" + cached.getValue();
    }

    private static Cache<SpecialItem, Long> specialItemFromStr(String cachedStr) {
        String[] split = cachedStr.split(":");
        ItemsManager itemsManager = DmarPvP.getInstance().getItemsManager();
        return new Cache<>(itemsManager.getItem(split[0]), Long.parseLong(split[1]));
    }

    public static Map<SpecialItem, Long> deserializeItemsMap(String map) {

        String[] split = map.split(",");

        Map<SpecialItem, Long> mapObj = new ConcurrentHashMap<>();
        for(String s : split) {
            Cache<SpecialItem, Long> cached = specialItemFromStr(s);
            mapObj.put(cached.getKey(), cached.getValue());
        }

        return mapObj;
    }

    public static String serializeItemsMap(Map<SpecialItem, Long> mapObj) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Map.Entry<SpecialItem, Long> entry : mapObj.entrySet()) {
            builder.append(Conversions.specialItemToStr(new Cache<>(entry.getKey(), entry.getValue())));
            if(i != mapObj.size()-1) {
                builder.append(",");
            }
            i++;
        }
        return builder.toString();
    }


}
