package dev.mqzn.dmar.menus.api;

import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class Menu {

    private final String title;
    private final int size;
    protected final Inventory inventory;

    public Menu(String title, int rows) {
        this.title = title;
        if(rows <1 || rows > 6) {
            throw new IndexOutOfBoundsException("Rows can't be less than 1 or greater than 6");
        }
        this.size = rows*9;
        this.inventory = Bukkit.createInventory(null, size, Formatter.color(title));
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public abstract void buildButtons(Player player);
    public abstract void handleOnClick(InventoryClickEvent e);

    public void open(Player player) {
        this.buildButtons(player);
        player.openInventory(inventory);
    }


}
