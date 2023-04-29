package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepPresentationMessage extends MineIntoTheDeepMessage<Integer> {
    public MineIntoTheDeepPresentationMessage(String teamName) {
        super(teamName);
    }

    @Override
    public MineIntoTheDeepResponse<Integer> parse(String[] responses) {
        if (responses.length != 2)
            throw new IllegalArgumentException("The response to presentation must have 2 parameters");

        try {
            return new MineIntoTheDeepResponse<>(Integer.parseInt(responses[1]), null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The response to presentation must have an integer as second parameter, received: " + responses[1] + " instead");
        }
    }
}
