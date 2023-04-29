package fr.iutdijon.mineintothedeep.css117.player;

public enum PickaxeUpgrade {
    DIAMOND(400, null),
    IRON(200, PickaxeUpgrade.DIAMOND),
    WOODEN(null, PickaxeUpgrade.IRON);

    private final Integer cost;
    private final PickaxeUpgrade nextUpgrade;

    PickaxeUpgrade(Integer cost, PickaxeUpgrade nextUpgrade) {
        this.cost = cost;
        this.nextUpgrade = nextUpgrade;
    }

    public Integer getCost() {
        return cost;
    }

    public PickaxeUpgrade getNextUpgrade() {
        return nextUpgrade;
    }
}
