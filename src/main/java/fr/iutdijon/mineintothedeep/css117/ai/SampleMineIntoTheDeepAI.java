package fr.iutdijon.mineintothedeep.css117.ai;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;
import fr.iutdijon.mineintothedeep.css117.player.PickageUpgrade;

public class SampleMineIntoTheDeepAI implements MineIntoTheDeepAI {

    private int dwarfAmount = 1;

    @Override
    public void play(IMineIntoTheDeepPlayer player) {
        // It's our turn!

        // Get the current map and the current scores
        MineIntoTheDeepMap map = player.getMap();
        MineIntoTheDeepScores scores = player.getScores();
        int myScore = scores.getScore(player.getMyPlayerId());

        int actionPoint = 2;

        int bestX = -1, bestY = -1, bestValue = -1;
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getWidth(); y++) {
                if (map.getCell(x, y).getOreType().getValue() > bestValue) {
                    bestX = x;
                    bestY = y;
                    bestValue = map.getCell(x, y).getOreType().getValue();
                }
            }
        }

        if (map.getCell(bestX, bestY).getOwner() == -1)
            player.moveDwarf(0, bestX, bestY);

        /*for (int i = 0; i < this.dwarfAmount; i++) {
            if (actionPoint > 0) {
                PickageUpgrade pickageUpgrade = player.getPickaxeUpgrade(i);
                if (pickageUpgrade.getNextUpgrade() != null && pickageUpgrade.getNextUpgrade().getCost() <= myScore) {
                    player.upgradePickaxe(i);
                    myScore -= pickageUpgrade.getCost();
                    actionPoint--;
                }
            }
            else
                break;
        }*/

        player.endOfTurn();
    }
}
