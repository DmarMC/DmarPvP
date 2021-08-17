package dev.mqzn.dmar.core;

import dev.mqzn.dmar.core.battlecry.BaseBattleCry;
import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.ranks.Rank;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.perks.base.Perk;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class UserData implements Comparable<UserData> {

    private final UUID id;
    private  int points, coins, kills, deaths, killStreak, highestKillStreak;
    private Kit kit = null;
    private Rank rank;
    private RodTrail rodTrail;
    private ArrowsTrail arrowsTrail;
    private BaseBattleCry battleCry;
    private final Map<SpecialItem, Long> specialItems;
    private Map<Integer, Perk> selectedPerks;

    public final static long ITEMS_TIME_IN_MS = TimeUnit.MINUTES.toMillis(10);

    public UserData(UUID id, int points, int coins, int kills, int deaths, int highestKillStreak,
                    BaseBattleCry battleCry, RodTrail rodTrail, ArrowsTrail arrowsTrail,
                    Map<Integer, Perk> selectedPerks, Map<SpecialItem, Long> specialItems) {

        this.id = id;
        this.points = points;
        this.coins = coins;
        this.kills = kills;
        this.deaths = deaths;
        this.killStreak = 0;
        this.highestKillStreak = highestKillStreak;
        this.rodTrail = rodTrail;
        this.arrowsTrail = arrowsTrail;
        this.rank = DmarPvP.getInstance().getRankManager().getDefaultRank();
        this.battleCry = battleCry;
        this.selectedPerks = selectedPerks;
        this.specialItems = specialItems;


    }

    public UserData(UUID id) {
        this(id, 1000, 500, 0,
                0, 0, null, null,
                null, new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }

    public BaseBattleCry getBattleCry() {
        return battleCry;
    }

    public void setBattleCry(BaseBattleCry battleCry) {
        this.battleCry = battleCry;
    }

    public UUID getId() {
        return id;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setSelectedPerks(Map<Integer, Perk> selectedPerks) {
        this.selectedPerks = selectedPerks;
    }

    public ArrowsTrail getArrowsTrail() {
        return arrowsTrail;
    }

    public void setArrowsTrail(ArrowsTrail arrowsTrail) {
        this.arrowsTrail = arrowsTrail;
    }

    public RodTrail getRodTrail() {
        return rodTrail;
    }

    public void setRodTrail(RodTrail rodTrail) {
        this.rodTrail = rodTrail;
    }

    public Kit getKit() {
        return kit;
    }

    public int getHighestKillStreak() {
        return highestKillStreak;
    }

    public void setHighestKillStreak(int highestKillStreak) {
         this.highestKillStreak = highestKillStreak;
    }


    public Map<SpecialItem, Long> getSpecialItems() {
        return specialItems;
    }

    public boolean hasItem(Class<? extends SpecialItem> clazz) {
        for (SpecialItem item : specialItems.keySet()) {
            if(clazz.isAssignableFrom(item.getClass())) {
                return true;
            }
        }
        return false;
    }


    public boolean hasItem(SpecialItem item) {
        return specialItems.containsKey(item);
    }

    public void addSpecialItem(SpecialItem item) {
        specialItems.put(item, System.currentTimeMillis());
    }


    public Perk getPerkAt(int slot) {
        return this.selectedPerks
                .getOrDefault(slot, null);
    }



    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Map<Integer, Perk> getSelectedPerks() {
        return selectedPerks;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPoints() {
        return points;
    }

    public int getCoins() {
        return coins;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public double getKDR() {
        if(deaths == 0) {
            return kills;
        }
        return ((double)kills/deaths);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */

    @Override
    public int compareTo(UserData o) {
        return o.getPoints()-this.getPoints();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData that = (UserData) o;
        return getPoints() == that.getPoints() &&
                getId().equals(that.getId())
                && getKills() == that.getKills()
                && getCoins() == that.getCoins() &&
                getDeaths() == this.getDeaths();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPoints());
    }


}
