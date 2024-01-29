package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class AppointmentFilterDTO extends PageableDTO {
  private UUID assistantId;
  private UUID patientId;
  private UUID doctorId;

  @Size(max = 100)
  private String consultationReason;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date fromDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date toDate;
}
