package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Doctor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorBodyDTO {
  private String name;
  private String specialty;
  private String contactInfo;

  public Doctor toDoctor() {
    Doctor doctor = new Doctor();
    doctor.setName(this.name);
    doctor.setSpecialty(this.specialty);
    doctor.setContactInfo(this.contactInfo);
    return doctor;
  }
}
