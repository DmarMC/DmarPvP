package dev.mqzn.dmar.backend.db;

import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.util.Conversions;

public enum DataUpdate {

    KILLS("Kills", UpdateType.INT),
    DEATHS("Deaths", UpdateType.INT),
    HIGHEST_KS("HighestKS", UpdateType.INT),
    COINS("Coins", UpdateType.INT),
    POINTS("Points", UpdateType.INT),
    ROD_TRAIL("RodTrail", UpdateType.STRING),
    ARROW_TRAIL("ArrowTrail", UpdateType.STRING),
    BATTLE_CRY("BattleCry", UpdateType.STRING),
    SELECTED_PERKS("SelectedPerks", UpdateType.STRING),
    SPECIAL_ITEMS("SpecialItems", UpdateType.STRING);

    private final String column;
    private final UpdateType updateType;
    DataUpdate(String column, UpdateType updateType) {
        this.column = column;
        this.updateType = updateType;
    }

    public String getColumn() {
        return column;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public Object getValue(UserData data) {
        Object v;
        switch (this) {
            case KILLS:
                v = data.getKills();
                break;
            case DEATHS:
                v = data.getDeaths();
                break;
            case HIGHEST_KS:
                v = data.getHighestKillStreak();
                break;
            case COINS:
                v = data.getCoins();
                break;
            case POINTS:
                v = data.getPoints();
                break;
            case BATTLE_CRY:
                v = data.getBattleCry() != null ? data.getBattleCry().getName() : "";
                break;
            case SELECTED_PERKS:
                v = Conversions.serializePerksMap(data.getSelectedPerks());
                break;
            case ROD_TRAIL:
                v = data.getRodTrail() != null ? data.getRodTrail().getName() : "";
                break;
            case ARROW_TRAIL:
                v = data.getArrowsTrail() != null ? data.getArrowsTrail().getName() : "";
                break;
            case SPECIAL_ITEMS:
                v = Conversions.serializeItemsMap(data.getSpecialItems());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
        return v;
    }



    public enum UpdateType {

        STRING,
        INT,
        LONG,
        BYTES;

    }



}
