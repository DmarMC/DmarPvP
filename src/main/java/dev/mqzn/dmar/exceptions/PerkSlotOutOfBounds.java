package dev.mqzn.dmar.exceptions;

import dev.mqzn.dmar.core.perks.base.Perk;

public class PerkSlotOutOfBounds extends Exception {


    public PerkSlotOutOfBounds(Perk perk, int slot) {
        super("Perk's slot is out of bounds, PerkName: " +
                slot + " Slot: " + slot);
    }




}
