package fr.iutdijon.mineintothedeep.css117.map;

public class MineIntoTheDeepMapCell
{
    private int x;
    private int y;
    private final int depth;
    private final int oreAmount;
    private final MineIntoTheDeepOreType oreType;
    private final int owner;

    public MineIntoTheDeepMapCell(int depth, int oreAmount, MineIntoTheDeepOreType oreType, int owner) {
        this.depth = depth;
        this.oreAmount = oreAmount;
        this.oreType = oreType;
        this.owner = owner;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDepth() {
        return depth;
    }

    public int getOreAmount() {
        return oreAmount;
    }

    public MineIntoTheDeepOreType getOreType() {
        return oreType;
    }

    public int getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "MineIntoTheDeepMapCell{" +
                "x=" + x +
                ", y=" + y +
                ", depth=" + depth +
                ", oreAmount=" + oreAmount +
                ", oreType=" + oreType +
                ", owner=" + owner +
                '}';
    }
}
