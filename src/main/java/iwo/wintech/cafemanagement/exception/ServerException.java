package iwo.wintech.cafemanagement.exception;

public class ServerException extends ErrorException {
    public ServerException(final String message, final String key) {
        super(message, key);
    }
}
