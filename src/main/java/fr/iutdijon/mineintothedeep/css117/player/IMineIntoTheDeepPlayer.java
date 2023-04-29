package fr.iutdijon.mineintothedeep.css117.player;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.message.MineIntoTheDeepSonarMessage;

public interface IMineIntoTheDeepPlayer {
    int getTurnNumber();
    void endOfTurn();
    MineIntoTheDeepMap getMap();
    void moveDwarf(int dwarfId, int dx, int dy);
    void removeDwarf(int dwarfId);
    void hireDwarf();
    void upgradeDwarf(int dwarfId);
    void sabotage(int playerId);
    MineIntoTheDeepScores getScores();
    MineIntoTheDeepSonarMessage.MineIntoTheDeepSonarResponse sonar(int x, int y);
}
