package hangouh.me.medi.link.v1.controllers.exceptions;

import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      @NonNull Exception ex,
      @Nullable Object body,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode statusCode,
      @NonNull WebRequest request) {

    HttpStatus httpStatus = HttpStatus.resolve(statusCode.value());
    if (httpStatus == null) {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(new ResponseDTO<>(httpStatus, ex.getMessage(), null), httpStatus);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              errors.put(fieldName, message);
            });

    return new ResponseEntity<>(
        new ResponseDTO<>(HttpStatus.BAD_REQUEST, "MethodArgumentNotValid", errors),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseDTO<String>> handleDataIntegrityViolation(
      DataIntegrityViolationException ex) {
    return ResponseUtil.createResponseEntity(
        null, HttpStatus.CONFLICT, Objects.requireNonNull(ex.getRootCause()).getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(
      DataIntegrityViolationException ex) {
    return ResponseUtil.createResponseEntity(
        null, HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getRootCause()).getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseDTO<String>> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    return ResponseUtil.createResponseEntity(
        null, HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getMessage()));
  }
}
