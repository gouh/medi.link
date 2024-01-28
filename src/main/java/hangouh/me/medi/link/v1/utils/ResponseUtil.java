package hangouh.me.medi.link.v1.utils;

import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
  public static <T> ResponseEntity<ResponsePageDTO<T>> createResponsePage(
      Page<T> body, HttpStatus status) {
    ResponsePageDTO<T> response = new ResponsePageDTO<>(HttpStatus.OK, "", body);
    return ResponseEntity.status(status).body(response);
  }

  public static <T> ResponseEntity<ResponseDTO<T>> createResponseEntity(
      T body, HttpStatus status, String errorMsg) {
    ResponseDTO<T> response = new ResponseDTO<>(status, errorMsg, body);
    return ResponseEntity.status(status).body(response);
  }

  public static <T> ResponseEntity<ResponseDTO<T>> createResponseEntity(T body, HttpStatus status) {
    return createResponseEntity(body, status, "");
  }
}
