package fr.iutdijon.mineintothedeep.css117;

import java.util.Map;

public class MineIntoTheDeepScores {
    private final Map<Integer, Integer> scores;

    public MineIntoTheDeepScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public Integer getScore(int playerId) {
        return scores.get(playerId);
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }
}
