package fr.iutdijon.mineintothedeep.css117.player;

public enum PickageUpgrade {
    DIAMOND(400, null),
    IRON(200, PickageUpgrade.DIAMOND),
    WOODEN(null, PickageUpgrade.IRON);

    private final Integer cost;
    private final PickageUpgrade nextUpgrade;

    PickageUpgrade(Integer cost, PickageUpgrade nextUpgrade) {
        this.cost = cost;
        this.nextUpgrade = nextUpgrade;
    }

    public Integer getCost() {
        return cost;
    }

    public PickageUpgrade getNextUpgrade() {
        return nextUpgrade;
    }
}
