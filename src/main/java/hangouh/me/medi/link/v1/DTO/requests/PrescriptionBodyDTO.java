package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.models.Prescription;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionBodyDTO {
  @NotNull @NotEmpty private String prescribedMedication;
  @NotNull @NotEmpty private String dosage;
  @NotNull @NotEmpty private String duration;
  @NotNull private UUID patientId;
  @NotNull private UUID doctorId;

  public Prescription toPrescription(Patient patient, Doctor doctor) {
    Prescription prescription = new Prescription();
    prescription.setPrescribedMedication(this.prescribedMedication);
    prescription.setDosage(this.dosage);
    prescription.setDuration(this.duration);
    prescription.setPatient(patient);
    prescription.setDoctor(doctor);
    return prescription;
  }
}
