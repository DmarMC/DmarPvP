package dev.mqzn.dmar.core.kits.base;

import dev.mqzn.dmar.core.managers.KitManager;
import dev.mqzn.dmar.util.Formatter;
import dev.mqzn.dmar.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class FireItem extends KitItem {


    private int initialCount;
    private final ItemStack item;

    public FireItem(Kit kit, int slot) {
        super(kit, slot, Material.FLINT_AND_STEEL, 1);
        this.setInitialCount(1);
        item = ItemBuilder.construct()
                .create(this.getType(), this.getAmount(), this.getDamage())
                .addFlags(ItemFlag.values())
                .setDisplay("&6Fire &e" + initialCount)
                .build();
    }
    public FireItem(Kit kit, int slot, int initialCount) {
        super(kit, slot, Material.FLINT_AND_STEEL, 1);
        this.setInitialCount(initialCount);
        item = ItemBuilder.construct()
                .create(this.getType(), this.getAmount(), this.getDamage())
                .addFlags(ItemFlag.values())
                .setDisplay("&6Fire &e" + initialCount)
                .build();
    }

    public FireItem(Kit kit) {
        this(kit, 8);
    }

    @Override
    public short getDamage() {
        return 63;
    }

    @Override
    public ItemStack toItemStack() {
        return item;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public void updateFor(Player player, int newCount, int itemSlot) {

        ItemStack clone = player.getInventory().getItem(itemSlot);
        ItemMeta meta = clone.getItemMeta();
        meta.setDisplayName(Formatter.color("&6Fire &e"+ newCount));
        clone.setItemMeta(meta);
        player.getInventory().setItem(itemSlot, clone);
        player.updateInventory();
    }

    public int getCount() {
        return initialCount;
    }

    public void onInteract(PlayerInteractEvent e, KitManager kitManager) {
        Player player = e.getPlayer();
        if(initialCount <= 0) {
            e.setCancelled(true);
            Formatter.sendMsg(player, "&cYou need to kill others to get more fire charges");
            return;
        }
        initialCount--;
        kitManager.setFireCount(player, initialCount);
    }



}
