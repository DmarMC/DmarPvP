package dev.mqzn.dmar.core.managers;

import dev.mqzn.dmar.core.ranks.Rank;
import dev.mqzn.dmar.core.UserData;
import java.util.*;

public class RankManager {

    private final Map<Integer, Rank> ranks;

    private final static int CAPACITY;
    static {
        CAPACITY = 37;
    }

    public RankManager() {
        ranks = new LinkedHashMap<>(37);
        this.storeRanks();
    }

    private void storeRanks() {

        String[] names = new String[]{"Hunter", "Knight", "Pirate", "Colonel", "Admiral", "Hero", "Master"};
        String[] colors = new String[]{"&b&l", "&9&l", "&3&l", "&6&l", "&c&l", "&2&l", "&f&l"};

        ranks.put(0, new Rank(0, "UNRANKED", 0, 0, "&e"));
        int p = 1500;
        int last = CAPACITY-1;
        for (int i = 1, l = 0, n = 0; i < last; i++) {
            l++;
            ranks.put(i, new Rank(i, names[n], l, p, colors[n]));
            p += 1250;

            if(l == 5) {
                l = 0;
                n++;
            }

        }

        ranks.put(last, new Rank(last, "Legend", 0, p - 1250 + 11000, "&5&l"));
    }


    public Map<Integer, Rank> getRanks() {
        return ranks;
    }

    public Rank calculateRank(UserData data) {

        int p = data.getPoints();
        int start = ranks.size()-1;


        while (start >=0) {
            if(p >= ranks.get(start).getStartPoints()) {
                return ranks.get(start);
            }
            start--;
        }

        return this.getDefaultRank();
    }

    public Rank getDefaultRank() {
        return ranks.get(0);
    }

    public int indexOfRank(Rank rank) {
        if(rank == null || !ranks.containsValue(rank)) return -1;
        return rank.getIndex();
    }

    public Rank getRankAt(int index) {
        return ranks.get(index);
    }

}
