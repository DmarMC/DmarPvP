package dev.mqzn.dmar.listeners;

import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.core.kits.base.FireItem;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.managers.KitManager;
import dev.mqzn.dmar.core.managers.TrailsManager;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.perks.base.ConsumablePerk;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsInteractionListener implements Listener {

    UserDataManager manager = DmarPvP.getInstance().getUserDataManager();
    KitManager kitManager = DmarPvP.getInstance().getKitManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        ItemStack item = e.getItem();
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        Action action = e.getAction();
        UserData data = manager.getData(player.getUniqueId());

        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            for(Perk perk : data.getSelectedPerks().values()) {
                if(perk instanceof ConsumablePerk
                        && item.getItemMeta().getDisplayName().equals(perk.getDisplayName())) {
                    ConsumablePerk consumablePerk = (ConsumablePerk) perk;
                    consumablePerk.onConsume(player, item);
                    e.setCancelled(true);
                    break;
                }
            }
        }

        //FireCounter !
        if(action == Action.RIGHT_CLICK_BLOCK && e.getMaterial() == Material.FLINT_AND_STEEL) {
            FireItem fireItem = kitManager.getFireItem(player.getUniqueId());
            if(fireItem == null) return;

            fireItem.onInteract(e, kitManager);
        }



        if(!data.getSpecialItems().isEmpty()) {

            data.getSpecialItems().forEach((sp, time) -> {
                if(e.getMaterial() == sp.getItem().getType() && (System.currentTimeMillis()-time) >= UserData.ITEMS_TIME_IN_MS) {
                    Kit kit = kitManager.getKitToApply(player);
                    data.setKit(kit);
                    kit.apply(player);
                    manager.updateCached(data);
                }

            });

        }


        TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();
        for(RodTrail rodTrail : trailsManager.getRodTrails()) {
            if(trailsManager.playerHasTrial(player, rodTrail, manager)) {
                rodTrail.onHookBack(e);
            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        Player player = e.getPlayer();

        ItemStack item = player.getItemInHand();
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        UserData data = manager.getData(player.getUniqueId());
        data.getSelectedPerks().values().forEach(cp -> {
            if(cp instanceof ConsumablePerk &&
                    cp.getDisplayName().equals(Formatter
                            .color(item.getItemMeta().getDisplayName()))) {

                e.setCancelled(true);
            }
        });

    }

}
