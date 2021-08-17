package dev.mqzn.dmar.core.combat;

public enum  KillMethod {

    SWORD("&cSword"),
    BOW("&bBow"),
    ROD("&aRod"),
    HANDS("&eHands"),
    FIRE_CHARGES("&6Fire Charges"),
    UNKNOWN("");


    private final String message;

    KillMethod(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
