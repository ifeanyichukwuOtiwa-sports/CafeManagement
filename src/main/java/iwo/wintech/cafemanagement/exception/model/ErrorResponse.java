package iwo.wintech.cafemanagement.exception.model;

import java.util.List;
import java.util.Map;

public record ErrorResponse(
        String error,
        String uuid,
        List<String> params,
        Map<String, Object> payload
) {
}
