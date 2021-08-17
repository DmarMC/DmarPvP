package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public abstract class ConsumablePerk extends Perk {

    protected ItemStack combatItem;
    private final Consumer<Player> actions;

    public ConsumablePerk() {
        super();

        combatItem = ItemBuilder.construct()
                .create(this.getIcon(), 1).setUnbreakable(true)
                .addFlags(ItemFlag.values())
                .setDisplay(Formatter.color(this.getDisplayName()))
                .setLore("&7Right-Click to use").build();

        actions = this.getActions();
    }

    public ItemStack getCombatItem() {
        return combatItem;
    }

    public abstract Consumer<Player> getActions();

    public void onConsume(Player player, ItemStack clickedItem) {
        this.actions.accept(player);

        int slot = player.getInventory().first(Material.SKULL_ITEM);
        clickedItem.setAmount(clickedItem.getAmount() - 1);

        ItemStack it = clickedItem.getAmount() <= 0 ? new ItemStack(Material.AIR) : clickedItem;
        player.getInventory().setItem(slot, it);

        player.updateInventory();

    }


}
