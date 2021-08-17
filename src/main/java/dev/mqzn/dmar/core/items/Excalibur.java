package dev.mqzn.dmar.core.items;

import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Material;

public class Excalibur extends SpecialItem{

    public Excalibur() {
        super(Material.DIAMOND_SWORD);
    }

    @Override
    public String getName() {
        return "excalibur";
    }

    @Override
    public String getDisplayName() {
        return Formatter.color("&3Excalibur Sword");
    }

    @Override
    public int getMenuSlot() {
        return 12;
    }

    @Override
    public String getDescription() {
        return "&7Legendary sword that has a chance of dealing 15+ damage";
    }


    @Override
    public int getPrice() {
        return 35000;
    }


}
