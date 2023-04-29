package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepResponse<T> {
    private final String errorMessage;
    private final T response;

    public MineIntoTheDeepResponse(T response, String errorMessage) {
        this.errorMessage = errorMessage;
        this.response = response;
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResponse() {
        return response;
    }
}
