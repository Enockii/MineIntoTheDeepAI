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
        switch (stringType) {
            case "RIEN": return MineIntoTheDeepOreType.NOTHING;
            case "FER": return MineIntoTheDeepOreType.IRON;
            case "OR": return MineIntoTheDeepOreType.GOLD;
            case "DIAMANT": return MineIntoTheDeepOreType.DIAMOND;
            case "MITHRIL": return MineIntoTheDeepOreType.MITHRIL;
            default: throw new IllegalArgumentException("The ore type " + stringType + " is not valid");
        }
    }
}
