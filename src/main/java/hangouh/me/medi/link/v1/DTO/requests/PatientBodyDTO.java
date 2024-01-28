package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Patient;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class PatientBodyDTO {
  @NotNull @NotEmpty
  @Size(max = 100)
  private String name;

  @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date dob;

  @NotNull @NotEmpty
  @Pattern(regexp = "[M,F]")
  private String gender;

  @NotNull @NotEmpty
  @Size(max = 150)
  private String contactInfo;

  @Nullable @Valid private UserBodyDTO user;

  public Patient toPatient() {
    Patient patient = new Patient();
    patient.setName(this.name);
    patient.setDob(this.dob);
    patient.setGender(this.getGender());
    patient.setContactInfo(this.contactInfo);
    return patient;
  }

  public char getGender() {
    return gender.charAt(0);
  }
}
