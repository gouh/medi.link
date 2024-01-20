package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.MedicalHistory;
import hangouh.me.medi.link.v1.models.Patient;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalHistoryBodyDTO {
  private UUID patientId;
  private String diseaseHistory;
  private String allergies;
  private String previousSurgeries;

  public MedicalHistory toMedicalHistory(Patient patient) {
    MedicalHistory medicalHistory = new MedicalHistory();
    medicalHistory.setPatient(patient);
    medicalHistory.setDiseaseHistory(this.diseaseHistory);
    medicalHistory.setAllergies(this.allergies);
    medicalHistory.setPreviousSurgeries(this.previousSurgeries);
    return medicalHistory;
  }
}
