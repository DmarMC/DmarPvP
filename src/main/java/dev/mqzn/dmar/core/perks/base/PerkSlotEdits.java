package dev.mqzn.dmar.core.perks.base;

import dev.mqzn.dmar.util.BigCache;
import dev.mqzn.dmar.util.Cache;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PerkSlotEdits extends ConcurrentHashMap<UUID, BigCache<Integer, Perk, SlotItemStatus> > {

    //just to solidify the logic a bit

}
