package fr.iutdijon.mineintothedeep.css117.map;

import java.util.List;

public class MineIntoTheDeepMap {
    private final int width;
    private final List<MineIntoTheDeepMapCell> cells;
    public MineIntoTheDeepMap(List<MineIntoTheDeepMapCell> cells) {
        if (cells == null)
            throw new IllegalArgumentException("The cells cannot be null");

        int i = 20;
        for (; i >= 0; i--) {
            if (cells.size() == i * i)
                break;
        }

        if (i == 0)
            throw new IllegalArgumentException("The cells must be a square");

        this.width = i;
        this.cells = cells;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < width; y++)
                cells.get(y * width + x).setCoordinates(x, y);
    }

    public int getWidth() {
        return width;
    }

    public MineIntoTheDeepMapCell getCell(int x, int y) {
        if (x < 0 || x >= this.width)
            throw new IllegalArgumentException("The x must be between 0 and " + width);

        if (y < 0 || y >= this.width)
            throw new IllegalArgumentException("The y must be between 0 and " + width);

        return cells.get(y * width + x);
    }

    public List<MineIntoTheDeepMapCell> getCells() {
        return cells;
    }

    public MineIntoTheDeepMapCell getBetterCell(boolean whereIsNoOne, int maxDepthExclusive) {
        MineIntoTheDeepMapCell betterCell = null;
        for (MineIntoTheDeepMapCell cell : cells) {

            if ((betterCell == null || cell.getOreType().getValue() > betterCell.getOreType().getValue()) && (!whereIsNoOne || cell.getOwner() == -1) && cell.getDepth() < maxDepthExclusive - 1)
                betterCell = cell;
            /*if ((betterCell == null || cell.getOreType().getValue() > betterCell.getOreType().getValue()) ) {
                if (cell.getOwner() == -1) {
                    if (cell.getDepth() < maxDepthExclusive - 1) {
                        betterCell = cell;
                    }
                }
            }*/
        }
        return betterCell;
    }
}
