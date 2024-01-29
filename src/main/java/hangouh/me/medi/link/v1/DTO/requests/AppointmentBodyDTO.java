package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Appointment;
import hangouh.me.medi.link.v1.models.Assistant;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.models.Patient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class AppointmentBodyDTO {
  @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date dateTime;

  @NotNull @NotEmpty private String consultationReason;
  @NotNull private UUID patientId;
  @NotNull private UUID doctorId;
  @NotNull private UUID assistantId;

  public Appointment toAppointment(Patient patient, Doctor doctor, Assistant assistant) {
    Appointment appointment = new Appointment();
    appointment.setDateTime(this.dateTime);
    appointment.setConsultationReason(this.consultationReason);
    appointment.setPatient(patient);
    appointment.setDoctor(doctor);
    appointment.setAssistant(assistant);
    return appointment;
  }
}
