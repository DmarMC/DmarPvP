package dev.mqzn.dmar.core.ranks;

import dev.mqzn.dmar.util.Formatter;
import org.bukkit.ChatColor;
import java.util.Objects;

public class Rank implements Comparable<Rank> {

    private final String name, displayName;
    private final int startPoints, level, index;
    private final String color;

    public Rank(int index, String name, int level, int startPoints, ChatColor color) {
        this(index, name, level, startPoints, color.toString());
    }

    public Rank(int index, String name, int level, int startPoints, String color) {
        this.index = index;
        this.name = name;
        this.level = level;
        this.startPoints = startPoints;
        this.color = color;
        this.displayName = getDisplay();
    }



    public String getName() {
        return name;
    }

    private String getDisplay() {
        String levelStr;
        switch (level) {

            case 1:
                levelStr = "I";
                break;
            case 2:
                levelStr = "II";
                break;
            case 3:
                levelStr = "III";
                break;
            case 4:
                levelStr = "IV";
                break;
            case 5:
                levelStr = "V";
                break;

            default: {
                levelStr = "";
            }
        }
        return Formatter.color(this.color + this.getName()
                + (levelStr.isEmpty() ? "" : (" " + levelStr)));
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getStartPoints() {
      return startPoints;
    }

    public int getLevel() {
        return level;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Rank o) {
        return o.getStartPoints()-this.getStartPoints();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rank)) return false;
        Rank rank = (Rank) o;
        return index == rank.getIndex() &&
                getStartPoints() == rank.getStartPoints() &&
                getName().equals(rank.getName()) &&
                getLevel() == rank.getLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStartPoints());
    }

}
