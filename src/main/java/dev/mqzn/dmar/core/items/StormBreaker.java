package dev.mqzn.dmar.core.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;

public class StormBreaker extends SpecialItem{

    public StormBreaker() {
        super(Material.IRON_AXE);
    }

    @Override
    public String getName() {
        return "stormbreaker";
    }

    @Override
    public String getDisplayName() {
        return "&8&lStorm&7&lBreaker";
    }

    @Override
    public int getMenuSlot() {
        return 14;
    }

    @Override
    public String getDescription() {
        return "Take the control of lightning in your advantage !";
    }

    @Override
    public int getPrice() {
        return 250000;
    }

    public void applyEffects(Player player) {

        Set<Material> set = new HashSet<>();
        set.add(Material.AIR);

        Block targetBlock = player.getTargetBlock(set, 120);
        player.getWorld().strikeLightning(targetBlock.getLocation());
    }

}
