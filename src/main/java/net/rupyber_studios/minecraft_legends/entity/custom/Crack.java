package net.rupyber_studios.minecraft_legends.entity.custom;

public enum Crack {
    NONE,
    LOW,
    HIGH;

    public static Crack from(float health) {
        if(health > 40) return NONE;
        else if(health > 20) return LOW;
        else return HIGH;
    }

    @Override
    public String toString() {
        return switch(this) {
            case NONE -> "none";
            case LOW -> "low";
            default -> "high";
        };
    }
}