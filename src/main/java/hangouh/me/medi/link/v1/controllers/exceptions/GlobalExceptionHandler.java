package hangouh.me.medi.link.v1.controllers.exceptions;

import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      @NotNull Exception ex,
      @Nullable Object body,
      @NotNull HttpHeaders headers,
      @NotNull HttpStatusCode statusCode,
      @NotNull WebRequest request) {

    HttpStatus httpStatus = HttpStatus.resolve(statusCode.value());
    if (httpStatus == null) {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(new ResponseDTO<>(httpStatus, ex.getMessage(), null), httpStatus);
  }
}
