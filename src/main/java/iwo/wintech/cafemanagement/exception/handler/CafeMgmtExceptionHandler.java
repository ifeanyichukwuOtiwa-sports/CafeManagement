package iwo.wintech.cafemanagement.exception.handler;

import iwo.wintech.cafemanagement.exception.factory.ExceptionFactory;
import iwo.wintech.cafemanagement.exception.model.ErrorResponse;
import iwo.wintech.cafemanagement.exception.model.ErrorTuple;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@RequiredArgsConstructor
@RestControllerAdvice
public class CafeMgmtExceptionHandler {
    private final ExceptionFactory<WebRequest> factory;

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handle(final Exception ex, final WebRequest request) {
        final ErrorTuple errorTuple = factory.create(ex, request);
        return this.convert(errorTuple);
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInputsExceptions(final RuntimeException ex, final WebRequest request) {
        final ErrorResponse body = new ErrorResponse(ex.getMessage(), UUID.randomUUID().toString(), null, null);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return ResponseEntity.badRequest().headers(headers).body(body);
    }


    private ResponseEntity<ErrorResponse> convert(final ErrorTuple errorTuple) {
        final HttpHeaders h = new HttpHeaders();
        errorTuple.headers().forEach(h::addAll);
        return new ResponseEntity<>(errorTuple.body(), h, errorTuple.statusCode());
    }
}
