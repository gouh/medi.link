package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionFilterDTO extends PageableDTO {
  private UUID patientId;
  private UUID doctorId;

  @Size(max = 100)
  private String prescribedMedication;

  @Size(max = 100)
  private String dosage;

  @Size(max = 100)
  private String duration;
}
