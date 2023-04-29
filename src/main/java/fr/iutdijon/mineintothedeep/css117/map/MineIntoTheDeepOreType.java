package fr.iutdijon.mineintothedeep.css117.map;

public enum MineIntoTheDeepOreType {
    NOTHING,
    IRON,
    DIAMOND,
    MITHRIL;

    public static MineIntoTheDeepOreType fromString(String stringType) {
        return switch (stringType) {
            case "RIEN" -> NOTHING;
            case "FER" -> IRON;
            case "DIAMANT" -> DIAMOND;
            case "MYTHRIL" -> MITHRIL;
            default -> throw new IllegalArgumentException("The ore type " + stringType + " is not valid");
        };
    }
}
