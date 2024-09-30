package iwo.wintech.cafemanagement.exception;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class ErrorException extends RuntimeException {
    private final String uuid = UUID.randomUUID().toString();
    private String message;
    private String key;
    private List<String> params = List.of();
    private Map<String, Object> payload;


    public ErrorException(final String message, final String key) {
        super(message);
        this.message = message;
        this.key = key;
    }

    public ErrorException(final String message, final Throwable cause, final String key) {
        super(message, cause);
        this.message = message;
        this.key = key;
    }

    public ErrorException setParams(List<String> params) {
        if (params != null) {
            this.params = params;
        }
        return this;
    }

    public ErrorException setParams(String... params) {
        if(params != null) {
            this.params = List.of(params);
        }
        return this;
    }

    public ErrorException setPayload(Map<String, Object> payload) {
        if(payload != null) {
            this.payload = payload;
        }
        return this;
    }

    @Override
    public String getMessage() {
        return String.format("%s %s", this.uuid, this.message);
    }
}
