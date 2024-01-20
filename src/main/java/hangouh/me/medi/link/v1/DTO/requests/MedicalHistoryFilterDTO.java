package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryFilterDTO extends PageableDTO {
  @Size(max = 255)
  private String diseaseHistory;

  @Size(max = 255)
  private String allergies;

  @Size(max = 255)
  private String previousSurgeries;
}
