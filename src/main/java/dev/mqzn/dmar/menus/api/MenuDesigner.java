package dev.mqzn.dmar.menus.api;

import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;

public class MenuDesigner {

    private final Menu menu;
    private static MenuDesigner instance;

    private MenuDesigner(Menu menu) {
        this.menu = menu;
    }

    public static MenuDesigner design(Menu menu) {
        if(instance == null) {
            instance = new MenuDesigner(menu);
        }
        return instance;
    }

    public void fill(int startSlot, int endSlot, GlassColor color, boolean glow) {


        if(endSlot > menu.inventory.getSize() || endSlot < 0) return;

        for (int i = startSlot; i < endSlot; i++) {
            menu.inventory.setItem(i, color.toItemStack(glow));
        }

    }

    public void fillAll(GlassColor color, boolean glow) {
        for(int i = 0; i < this.menu.inventory.getSize(); i++) {
            this.fill(color, glow, i);
        }
    }

    public void fill(GlassColor color, boolean glow, int... slots) {
        for(int slot : slots)
            menu.inventory.setItem(slot, color.toItemStack(glow));

    }


    public void fillSquared(GlassColor peripheral, GlassColor line, boolean glow) {

        int size = menu.inventory.getSize();
        int last = size-1;

        this.fill(peripheral, glow, 0, 8, last, last-8);

        this.fill(1, 8, line, glow);
        int next = last-8+1;

        this.fill(next, size-1, line, glow);

        for (int i = 9; i < (next-9); i = i+9) {
            this.fill(line, glow, i);
        }

        for(int i = 17; i < last-8; i = i+9) {
            this.fill(line, glow, i);
        }
    }


    public Menu getMenu() {
        return menu;
    }

    public List<Integer> getEmptySlots() {

        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < menu.inventory.getSize(); i++) {
            if(menu.inventory.getItem(i) == null || menu.inventory.getItem(i).getType() == Material.AIR) {
                emptySlots.add(i);
            }
        }

        return emptySlots;
    }



}
