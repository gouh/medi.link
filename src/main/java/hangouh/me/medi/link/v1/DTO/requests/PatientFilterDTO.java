package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientFilterDTO extends PageableDTO {
  private UUID patientId;

  @Size(max = 100)
  private String name;

  private Date dateOfBirth;

  @Pattern(regexp = "[M,F]")
  private String gender;

  @Size(max = 255)
  private String contactInfo;
}
