package fr.iutdijon.mineintothedeep.css117.message;

public abstract class MineIntoTheDeepMessage<T> {
    private final String message;
    private final String[] parameters;
    protected MineIntoTheDeepMessage(String message, String... parameters) {
        if (message == null)
            throw new IllegalArgumentException("The message cannot be null");

        this.message = message;
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public String[] getParameters() {
        return parameters;
    }

    public boolean waitingForResponse() {
        return true;
    }

    public abstract MineIntoTheDeepResponse<T> parse(String[] responses);
}
