package migros.one.logistic.exception;

import migros.one.logistic.dto.ErrorResponse;
import migros.one.logistic.error.BadArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExcHandler {

    @ExceptionHandler({BadArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadArgumentException(BadArgumentException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage()).status(exception.getStatusCode())
                .error(BadArgumentException.class.getName()).path(null).timestamp(LocalDateTime.now()).build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
