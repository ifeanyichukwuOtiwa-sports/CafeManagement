package iwo.wintech.cafemanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_ACTIVE("User is not yet active"),
    INVALID_USER_CREDENTIALS("Invalid User credentials"),
    INVALID_ADMIN_CREDENTIALS("Invalid admin credentials"),
    USER_TOKEN_NOT_PROVIDED("User Token not found"),
    ADMIN_TOKEN_NOT_PROVIDED("Admin Token not found");

    private final String key;

    public RequestException requestException(String message) {
        return new RequestException(this.key, message);
    }
}
