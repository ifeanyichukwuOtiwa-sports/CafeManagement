package iwo.wintech.cafemanagement.exception;

public class RequestException extends ErrorException {
    public RequestException(final String message, final String key) {
        super(message, key);
    }
}
