package iwo.wintech.cafemanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_ACTIVE("User is not yet active"),
    INVALID_CREDENTIALS("Invalid credentials");

    private final String key;

    public RequestException requestException(String message) {
        return new RequestException(this.key, message);
    }
}
