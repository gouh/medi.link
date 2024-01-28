package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Doctor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorBodyDTO {
  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String name;

  @NotNull
  @NotEmpty
  @Size(max = 255)
  private String specialty;

  @NotNull
  @NotEmpty
  @Size(max = 150)
  private String contactInfo;

  private UserBodyDTO user;

  public Doctor toDoctor() {
    Doctor doctor = new Doctor();
    doctor.setName(this.name);
    doctor.setSpecialty(this.specialty);
    doctor.setContactInfo(this.contactInfo);
    return doctor;
  }
}
