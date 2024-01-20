package hangouh.me.medi.link.v1.DTO.requests;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDTO {
  private int page = 0;
  private int size = 30;
  private Map<String, String> sortBy;
}
