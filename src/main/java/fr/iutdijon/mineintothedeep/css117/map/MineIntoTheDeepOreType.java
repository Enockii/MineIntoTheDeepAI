package fr.iutdijon.mineintothedeep.css117.map;

public enum MineIntoTheDeepOreType {
    NOTHING(0),
    IRON(10),
    GOLD(20),
    DIAMOND(40),
    MITHRIL(80);

    private final int value;
    MineIntoTheDeepOreType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MineIntoTheDeepOreType fromString(String stringType) {
        return switch (stringType) {
            case "RIEN" -> NOTHING;
            case "FER" -> IRON;
            case "OR" -> GOLD;
            case "DIAMANT" -> DIAMOND;
            case "MITHRIL" -> MITHRIL;
            default -> throw new IllegalArgumentException("The ore type " + stringType + " is not valid");
        };
    }
}
