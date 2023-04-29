package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepMoveDwarfMessage extends MineIntoTheDeepMessage<Void> {
    public MineIntoTheDeepMoveDwarfMessage(int dwarfId, int dx, int dy) {
        super(MineIntoTheDeepMessages.CLIENT_MOVE, Integer.toString(dwarfId), Integer.toString(dy), Integer.toString(dx));
    }

    @Override
    public MineIntoTheDeepResponse<Void> parse(String[] responses) {
        if (responses.length == 0)
            throw new IllegalArgumentException("The response cannot be null");

        if (responses[0].equals("OK"))
            return new MineIntoTheDeepResponse<>(null, null);
        else
            return new MineIntoTheDeepResponse<>(null, responses[1]);
    }
}
