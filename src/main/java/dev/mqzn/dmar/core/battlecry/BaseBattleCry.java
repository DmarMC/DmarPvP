package dev.mqzn.dmar.core.battlecry;

import com.google.common.base.Objects;

public abstract class BaseBattleCry implements BattleCry {

    private final String permission;

    public BaseBattleCry() {
        this.permission = "pvp.battlecry." + this.getName().toLowerCase();
    }

    @Override
    public String getPermission() {
        return permission;
    }


    @Override
    public int getPrice() {
        return 12000;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseBattleCry)) return false;
        BaseBattleCry that = (BaseBattleCry) o;

        if(this.getName() == null || that.getName() == null) return false;

        return Objects.equal(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPermission());
    }


}
