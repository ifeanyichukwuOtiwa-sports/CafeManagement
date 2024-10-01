package iwo.wintech.cafemanagement.exception.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public record ErrorTuple(
        ErrorResponse body,
        Integer statusCode,
        Map<String, List<String>> headers
) {

    public void addHeaders(String key, String value) {
        final List<String> values = headers.computeIfAbsent(key, k -> new LinkedList<>());
        values.add(value);
    }
}
