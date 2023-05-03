package fr.iutdijon.mineintothedeep.css117.ai;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMapCell;
import fr.iutdijon.mineintothedeep.css117.message.MineIntoTheDeepSonarMessage;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;
import fr.iutdijon.mineintothedeep.css117.player.PickaxeUpgrade;

import java.awt.*;

public class SampleMineIntoTheDeepAI implements MineIntoTheDeepAI {

    private final int HIRING_COST = 250;

    private int dwarfAmount = 1;
    private int maxKnownDepth = 10;
    private boolean lastLayerIsKnown = false;
    private boolean[] dwarfToRemove = new boolean[] { false, false, false };
    private boolean[] dwarfRemoved = new boolean[] { false, false, false };

    @Override
    public void play(IMineIntoTheDeepPlayer player) {
        // It's our turn!


        // Get the current map and the current scores
        MineIntoTheDeepMap map = player.getMap();
        MineIntoTheDeepScores scores = player.getScores();
        int myScore = scores.getScore(player.getMyPlayerId());

        int actionPoint = 2;

        for (int dwarfId = 0; dwarfId < dwarfAmount; dwarfId++)
        {

            if (actionPoint <= 0)
                break;

            if (dwarfRemoved[dwarfId])
                continue;

            Point dwarfPositionCoordinates = player.getDwarfPosition(dwarfId);
            MineIntoTheDeepMapCell dwarfCell = dwarfPositionCoordinates != null ? map.getCell(dwarfPositionCoordinates.x, dwarfPositionCoordinates.y) : null;
            int depth = dwarfCell != null ? dwarfCell.getDepth() : 0;

            if (depth + 1 == maxKnownDepth)
            {
                if (!lastLayerIsKnown)
                    actionPoint = refreshMaxKnownDepth(player, actionPoint, dwarfCell);

                if (depth + 1 == maxKnownDepth) {
                    MineIntoTheDeepMapCell bestCell = map.getBetterCell(true, maxKnownDepth);
                    if (bestCell == null) {
                        player.removeDwarf(dwarfId);
                        dwarfRemoved[dwarfId] = true;
                        actionPoint--;
                        continue;
                    } else {
                        player.moveDwarf(dwarfId, bestCell.getX(), bestCell.getY());
                        actionPoint--;
                    }
                }

                if (lastLayerIsKnown && maxKnownDepth - depth <= 4 && actionPoint > 0) {
                    MineIntoTheDeepSonarMessage.MineIntoTheDeepSonarResponse sonarResponse = player.sonar(dwarfPositionCoordinates.x, dwarfPositionCoordinates.y);
                    int sonarValue = sonarResponse.getValueInHigherLayer() + sonarResponse.getValueInHigherLayerMinus1() + sonarResponse.getValueInHigherLayerMinus2() + sonarResponse.getValueInHigherLayerMinus3();
                    actionPoint--;

                    if (actionPoint > 0 && sonarValue <= 0) {
                        MineIntoTheDeepMapCell bestCell = map.getBetterCell(true, maxKnownDepth);
                        if (bestCell == null) {
                            player.removeDwarf(dwarfId);
                            dwarfRemoved[dwarfId] = true;
                            actionPoint--;
                            continue;
                        } else {
                            player.moveDwarf(dwarfId, bestCell.getX(), bestCell.getY());
                            actionPoint--;
                        }
                    }
                    else if (actionPoint < 1 && sonarValue <= 0) {
                        dwarfToRemove[dwarfId] = true;
                        continue;
                    }
                }

                if (actionPoint > 0 && dwarfToRemove[dwarfId]) {
                    MineIntoTheDeepMapCell bestCell = map.getBetterCell(true, maxKnownDepth);
                    if (bestCell == null) {
                        player.removeDwarf(dwarfId);
                        dwarfRemoved[dwarfId] = true;
                        actionPoint--;
                        continue;
                    } else {
                        player.moveDwarf(dwarfId, bestCell.getX(), bestCell.getY());
                        actionPoint--;
                    }
                }
            }
            else {
                PickaxeUpgrade upgrade = player.getPickaxeUpgrade(dwarfId);
                if (upgrade == PickaxeUpgrade.DIAMOND && myScore >= HIRING_COST && dwarfAmount < 3) {
                    player.hireDwarf();
                    dwarfAmount++;
                    actionPoint--;
                    myScore -= HIRING_COST;
                }
                else if (upgrade == PickaxeUpgrade.IRON && myScore >= PickaxeUpgrade.DIAMOND.getCost()) {
                    player.upgradePickaxe(dwarfId);
                    actionPoint--;
                    myScore -= PickaxeUpgrade.DIAMOND.getCost();
                }
                else if (upgrade == PickaxeUpgrade.WOODEN && myScore >= PickaxeUpgrade.IRON.getCost()) {
                    player.upgradePickaxe(dwarfId);
                    actionPoint--;
                    myScore -= PickaxeUpgrade.IRON.getCost();
                }

                dwarfPositionCoordinates = player.getDwarfPosition(dwarfId);
                dwarfCell = dwarfPositionCoordinates != null ? map.getCell(dwarfPositionCoordinates.x, dwarfPositionCoordinates.y) : null;
                depth = dwarfCell != null ? dwarfCell.getDepth() : 0;

                if (actionPoint == 0) {
                    continue;
                }

                if (depth < maxKnownDepth) {
                    MineIntoTheDeepMapCell bestCell = map.getBetterCell(true, maxKnownDepth);
                    if (bestCell == null && depth == maxKnownDepth-1) {
                        player.removeDwarf(dwarfId);
                        dwarfRemoved[dwarfId] = true;
                        actionPoint--;
                    }
                    else if ( bestCell != null && (dwarfCell == null || dwarfCell.getOreType().getValue() < bestCell.getOreType().getValue())) {
                        player.moveDwarf(dwarfId, bestCell.getX(), bestCell.getY());
                        actionPoint--;
                    }
                }
                else {
                    actionPoint = refreshMaxKnownDepth(player, actionPoint, dwarfCell);
                }
            }
        }

        int numberOfRemoved = 0;
        for (Boolean nain : dwarfRemoved) {
            if (nain)
                numberOfRemoved += 1;
        }
        player.endOfTurn();
    }

    private int refreshMaxKnownDepth(IMineIntoTheDeepPlayer player, int actionPoint, MineIntoTheDeepMapCell dwarfCell)
    {
        MineIntoTheDeepSonarMessage.MineIntoTheDeepSonarResponse sonarResponse = player.sonar(dwarfCell.getX(), dwarfCell.getY());
        actionPoint--;
        if (sonarResponse.getValueInHigherLayerMinus1() == -1) {
            maxKnownDepth = dwarfCell.getDepth() + 1;
            lastLayerIsKnown = true;
        }
        else if (sonarResponse.getValueInHigherLayerMinus2() == -1) {
            maxKnownDepth = dwarfCell.getDepth() + 2;
            lastLayerIsKnown = true;
        }
        else if (sonarResponse.getValueInHigherLayerMinus3() == -1) {
            maxKnownDepth = dwarfCell.getDepth() + 3;
            lastLayerIsKnown = true;
        }
        else
            maxKnownDepth = dwarfCell.getDepth() + 3;
        return actionPoint;
    }
}
