package hangouh.me.medi.link.v1.DTO.requests;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class DateTimeFilterDTO {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date fromDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date toDate;
}
