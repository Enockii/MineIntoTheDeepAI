package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepEndTurnMessage extends MineIntoTheDeepMessage<Void> {
    public MineIntoTheDeepEndTurnMessage() {
        super(MineIntoTheDeepMessages.CLIENT_END_TURN);
    }

    @Override
    public MineIntoTheDeepResponse<Void> parse(String[] responses) {
        if (responses.length == 0)
            throw new IllegalArgumentException("The response cannot be null");

        if (responses[0].equals("TOUR_FINI"))
            return new MineIntoTheDeepResponse<>(null, null);
        else
            return new MineIntoTheDeepResponse<>(null, responses[1]);
    }
}
