package iwo.wintech.cafemanagement.exception.factory;

import iwo.wintech.cafemanagement.exception.ErrorException;
import iwo.wintech.cafemanagement.exception.model.ErrorResponse;
import iwo.wintech.cafemanagement.exception.model.ErrorTuple;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExceptionFactory<T> {

    public ErrorTuple create(final Exception ex, final T requestInfo) {
        return switch(ex) {
            case ErrorException ee -> convertToTuple(ee, requestInfo);
            default -> getDefaultException(ex, requestInfo);
        };
    }

    private ErrorTuple getDefaultException(final Exception ex, final T requestInfo) {
        return getCause(ex)
                .map( e -> convertToTuple(e, requestInfo))
                .orElseGet(() -> convertToTuple(new ErrorException(ex.getMessage(), ex, "INTERNAL_SERVER_ERROR"), requestInfo));

    }

    private Optional<ErrorException> getCause(final Exception ex) {
        final Throwable cause = NestedExceptionUtils.getRootCause(ex);

        if (cause instanceof ErrorException errorException) {
            return Optional.of(errorException);
        }
        return Optional.empty();
    }

    private ErrorTuple convertToTuple(final ErrorException ee, final T requestInfo) {
        final String uuid = ee.getUuid();
        final String key = ee.getKey();
        final Map<String, List<String>> errorHeader = Map.ofEntries(
                Map.entry("Content-Type", List.of("application/json")),
                Map.entry("X-error-message", List.of("Server error")));
        final ErrorResponse errorResponse = new ErrorResponse(key, uuid, ee.getParams(), ee.getPayload());
        return new ErrorTuple(errorResponse, ee.getStatus(), errorHeader);
    }
}
