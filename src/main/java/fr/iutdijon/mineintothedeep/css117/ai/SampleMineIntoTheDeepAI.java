package fr.iutdijon.mineintothedeep.css117.ai;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;

public class SampleMineIntoTheDeepAI implements MineIntoTheDeepAI {
    @Override
    public void play(IMineIntoTheDeepPlayer player) {
        // It's our turn!

        // Get the current map and the current scores
        MineIntoTheDeepMap map = player.getMap();
        MineIntoTheDeepScores scores = player.getScores();

        throw new UnsupportedOperationException("Not implemented yet !");
    }
}
