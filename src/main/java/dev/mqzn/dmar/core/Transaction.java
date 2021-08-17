package dev.mqzn.dmar.core;

import dev.mqzn.dmar.core.items.SpecialItem;
import dev.mqzn.dmar.core.managers.PerkManager;
import dev.mqzn.dmar.core.perks.base.StatsRewardPerk;
import dev.mqzn.dmar.core.trails.ArrowsTrail;
import dev.mqzn.dmar.core.trails.RodTrail;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.kits.base.Kit;
import dev.mqzn.dmar.core.managers.UserDataManager;
import dev.mqzn.dmar.core.managers.TrailsManager;
import dev.mqzn.dmar.core.perks.base.Perk;
import dev.mqzn.dmar.core.trails.Trail;
import dev.mqzn.dmar.util.Formatter;
import org.bukkit.entity.Player;

public class Transaction {

    private static Player buyer;
    private static Purchasable product;

    private static final PerkManager perkManager;

    static {
        perkManager = DmarPvP.getInstance().getPerkManager();
    }

    public static void process(Player buyer, Purchasable product) {
        Transaction.buyer = buyer;
        Transaction.product = product;

        Transaction.processTransaction();
    }

    private static void processTransaction(){

        UserData data = getBuyerData();

        if(hasPermissionsFor()) {
            processHavingPerms();
        }else {
            if(!hasEnoughCoins(data) ) {
                Formatter.sendMsg(buyer, "&cYou Don't have enough coins to purchase that product !");
                buyer.closeInventory();
            }else {
                processNoPerms(data);
            }
        }

    }


    private static UserData getBuyerData() {
        return DmarPvP.getInstance().getUserDataManager().getData(buyer.getUniqueId());
    }
    private static boolean hasEnoughCoins(UserData data) {
        return data.getCoins() >= product.getPrice();
    }

    private static boolean hasPermissionsFor() {
        return buyer.hasPermission(product.getPermission());
    }

    private static void processHavingPerms() {
        UserDataManager userManager = DmarPvP.getInstance().getUserDataManager();
        UserData data = Transaction.getBuyerData();

        if(product instanceof Kit) {
            Kit kit = (Kit) product;

            kit.apply(buyer);
            data.setKit(kit);

            userManager.updateCached(data);
            Formatter.sendMsg(buyer, "&7You have equipped the kit &a" + kit.getDisplayName());
        }

        if(product instanceof Perk) {
            Perk perk = (Perk)product;
            if(perk instanceof StatsRewardPerk) {
                data.setKillStreak(0);
            }
            int slot = perkManager.addPerk(userManager, buyer, perk);
            if(slot == -1) return;
            Formatter.sendMsg(buyer, "&7You have added  the " + perk.getDisplayName() + " &7perk in &6Slot #" + slot);
        }

        TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();

        if(product instanceof Trail) {
            Trail trail = (Trail)product;

            if(trailsManager.playerHasTrial(buyer, trail, userManager)) {
                buyer.closeInventory();
                Formatter.sendMsg(buyer, "&cYou already have that trail equipped !");
            }else {
                if(trail instanceof RodTrail) {
                    RodTrail rodTrail = (RodTrail)trail;
                    data.setRodTrail(rodTrail);
                }

                if(trail instanceof ArrowsTrail) {
                    ArrowsTrail arrowsTrail = (ArrowsTrail)trail;
                    data.setArrowsTrail(arrowsTrail);
                }

                userManager.updateCached(data);

                buyer.closeInventory();
                Formatter.sendMsg(buyer, "&cYou have selected &a&l" + trail.getName() + " &7trial");
            }
        }

        else if(product instanceof SpecialItem) {

            SpecialItem specialItem = (SpecialItem)product;
            specialItem.apply(buyer);

            data.addSpecialItem(specialItem);
            userManager.updateCached(data);

            Formatter.sendMsg(buyer, "&7You have successfully purchased the Legendary " + specialItem.getDescription());
            Formatter.sendMsg(buyer, "&eIt will Expire in 10 minutes !");
        }

    }

    private static void processNoPerms(UserData data) {
        UserDataManager userManager = DmarPvP.getInstance().getUserDataManager();

        int change = data.getCoins() - product.getPrice();
        data.setCoins(Math.max(change, 0));


        userManager.updateCached(data);

        if(product instanceof Kit) {
            Kit kit = (Kit) product;

            kit.apply(buyer);
            data.setKit(kit);
            userManager.updateCached(data);

            Formatter.sendMsg(buyer, "&7You have successfully purchased the kit &6" + kit.getDisplayName());
        }else if(product instanceof Perk) {
            Perk perk = (Perk)product;

            if(perk instanceof StatsRewardPerk) {
                data.setKillStreak(0);
            }

            int slot = perkManager.addPerk(userManager, buyer, perk);
            if(slot == -1) return;
            userManager.fullUpdate(data);
            Formatter.sendMsg(buyer, "&7You have successfully purchased the " + perk.getDisplayName() + " &7perk");
            Formatter.sendMsg(buyer, "&7The perk " + perk.getDisplayName() + " &7has been added in &bSlot &3#" + slot);
        }else if(product instanceof SpecialItem) {

            SpecialItem specialItem = (SpecialItem)product;
            specialItem.apply(buyer);

            data.addSpecialItem(specialItem);
            userManager.updateCached(data);

            Formatter.sendMsg(buyer, "&7You have successfully purchased the Legendary " + specialItem.getDisplayName());
            Formatter.sendMsg(buyer, "&eIt will Expire in 10 minutes !");
        }

        /*if(product instanceof PurchasableItem) {
            PurchasableItem purchasableItem = (PurchasableItem) product;
            buyer.closeInventory();

            PurchasableItemManager manager = DmarPvP.getInstance().getPurchasableItemManager();

            if(manager.getPlayerItems(buyer).contains(purchasableItem)) {
                Formatter.sendMsg();(buyer, "&cYou already have that item equipped !");
                return;
            }

            if(purchasableItem.checkForRank(buyer)) {

                manager.processItemToInv(buyer, purchasableItem);
            }else {
                Helper.sendMessage(buyer, "&cYou need to have atleast " + purchasableItem.getMinimumRank().getDisplayName());
            }

        }*/

        else if(product instanceof Trail) {
            Trail trail = (Trail) product;


            TrailsManager trailsManager = DmarPvP.getInstance().getTrailsManager();
            if(trailsManager.playerHasTrial(buyer, trail, userManager)) {
                buyer.closeInventory();
                Formatter.sendMsg(buyer, "&cYou already have that trail equipped !");
            }else {
                if(trail instanceof RodTrail) {
                    RodTrail rodTrail = (RodTrail)trail;
                    data.setRodTrail(rodTrail);
                }

                if(trail instanceof ArrowsTrail) {
                    ArrowsTrail arrowsTrail = (ArrowsTrail)trail;
                    data.setArrowsTrail(arrowsTrail);
                }

                userManager.updateCached(data);

                buyer.closeInventory();
                Formatter.sendMsg(buyer, "&cYou have selected &a&l" + trail.getName() + " &7trial");
            }
        }

    }

}

