package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorFilterDTO extends PageableDTO {
  private UUID doctorId = null;

  @Size(max = 100)
  private String name = null;

  @Size(max = 255)
  private String specialty = null;

  @Size(max = 150)
  private String contactInfo = null;
}
