package fr.iutdijon.mineintothedeep.css117.ai;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMapCell;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;
import fr.iutdijon.mineintothedeep.css117.player.PickageUpgrade;

public class SampleMineIntoTheDeepAI implements MineIntoTheDeepAI {

    private final int HIRING_COST = 250;

    private int dwarfAmount = 1;

    @Override
    public void play(IMineIntoTheDeepPlayer player) {
        // It's our turn!

        // Get the current map and the current scores
        MineIntoTheDeepMap map = player.getMap();
        MineIntoTheDeepScores scores = player.getScores();
        int myScore = scores.getScore(player.getMyPlayerId());

        int actionPoint = 2;

        for (int i = 0; i < dwarfAmount; i++) {
            if (actionPoint <= 0)
                break;

            PickageUpgrade upgrade = player.getPickaxeUpgrade(i);
            if (upgrade == PickageUpgrade.DIAMOND && myScore >= HIRING_COST) {
                player.hireDwarf();
                actionPoint--;
                myScore -= HIRING_COST;
            }
            else if (upgrade == PickageUpgrade.IRON && myScore >= PickageUpgrade.DIAMOND.getCost()) {
                player.upgradePickaxe(i);
                actionPoint--;
                myScore -= PickageUpgrade.DIAMOND.getCost();
            }
            else if (upgrade == PickageUpgrade.WOODEN && myScore >= PickageUpgrade.IRON.getCost()) {
                player.upgradePickaxe(i);
                actionPoint--;
                myScore -= PickageUpgrade.IRON.getCost();
            }

            MineIntoTheDeepMapCell bestCell = map.getBetterCell(true);
            player.moveDwarf(i, bestCell.getX(), bestCell.getY());
            actionPoint--;
        }

        player.endOfTurn();
    }
}
