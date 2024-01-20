package hangouh.me.medi.link.v1.DTO.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class MetadataDTO {
  private int statusCode;
  private String statusMessage;
  private String error;

  public MetadataDTO(HttpStatus statusCode, String statusMessage, String error) {
    this.statusCode = statusCode.value();
    this.statusMessage = statusMessage;
    this.error = error;
  }
}
