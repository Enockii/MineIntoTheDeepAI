package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepUpgradePickaxeMessage extends MineIntoTheDeepMessage<Void> {
    public MineIntoTheDeepUpgradePickaxeMessage(int dwarfId) {
        super(MineIntoTheDeepMessages.CLIENT_UPGRADE, Integer.toString(dwarfId));
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
