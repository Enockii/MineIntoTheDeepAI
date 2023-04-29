package fr.iutdijon.mineintothedeep.css117.message;

import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MineIntoTheDeepScoresMessage extends MineIntoTheDeepMessage<MineIntoTheDeepScores> {
    public MineIntoTheDeepScoresMessage() {
        super(MineIntoTheDeepMessages.CLIENT_SCORES);
    }

    @Override
    public MineIntoTheDeepResponse<MineIntoTheDeepScores> parse(String[] responses) {
        if (responses.length == 0)
            throw new IllegalArgumentException("The response cannot be null");

        if (responses[0].equals("SCORES"))
        {
            Map<Integer, Integer> scores = new HashMap<>();
            for (int i = 1; i < responses.length; i++)
                scores.put(i - 1, Integer.parseInt(responses[i]));
            return new MineIntoTheDeepResponse<>(new MineIntoTheDeepScores(scores), null);
        }
        else
            return new MineIntoTheDeepResponse<>(null, "Unknown error");
    }
}
