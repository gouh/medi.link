package hangouh.me.medi.link.v1.DTO.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseDTO<T> {
  private MetadataDTO metadata;
  private T data;
  private PaginationDTO pagination = null;

  public ResponseDTO(HttpStatus httpStatus, String errorMsg, T data) {
    this.metadata = new MetadataDTO(httpStatus, httpStatus.getReasonPhrase(), errorMsg);
    this.data = data;
  }
}
