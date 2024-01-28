package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Patient;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class PatientBodyDTO {
  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String name;

  @NotNull
  @NotEmpty
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date dob;

  @Pattern(regexp = "[M,F]")
  private char gender;

  @NotNull
  @NotEmpty
  @Size(max = 150)
  private String contactInfo;

  private UserBodyDTO user;

  public Patient toPatient() {
    Patient patient = new Patient();
    patient.setName(this.name);
    patient.setDob(this.dob);
    patient.setGender(this.gender);
    patient.setContactInfo(this.contactInfo);
    return patient;
  }
}
