package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientFilterDTO {
  private UUID patientId = null;

  @Size(max = 100)
  private String name = null;

  private Date dateOfBirth;

  @Pattern(regexp = "[MF]")
  private Character gender = null;

  @Size(max = 255)
  private String contactInfo = null;

  private int page = 0;

  private int size = 30;

  private Map<String, String> sortBy;
}
