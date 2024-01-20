package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Patient;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientBodyDTO {
  private String name;
  private Date dob;
  private char gender;
  private String contactInfo;

  public Patient toPatient() {
    Patient patient = new Patient();
    patient.setName(this.name);
    patient.setDob(this.dob);
    patient.setGender(this.gender);
    patient.setContactInfo(this.contactInfo);
    return patient;
  }
}
