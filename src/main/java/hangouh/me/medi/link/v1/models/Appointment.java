package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "appointment")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "appointmentId")
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "appointment_id")
  private UUID appointmentId;

  @Column(name = "date_time")
  private Date dateTime;

  @Column(name = "consultation_reason")
  private String consultationReason;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @ManyToOne()
  @JoinColumn(name = "patient_id")
  @JsonManagedReference
  @JsonIgnoreProperties(value = {"prescriptions", "appointments", "medicalHistory", "user"})
  private Patient patient;

  @ManyToOne()
  @JoinColumn(name = "doctor_id")
  @JsonManagedReference
  @JsonIgnoreProperties(value = {"prescriptions", "appointments", "user"})
  private Doctor doctor;

  @ManyToOne()
  @JoinColumn(name = "assistant_id")
  @JsonManagedReference
  @JsonIgnoreProperties(value = {"appointments", "user"})
  private Assistant assistant;

  public void setPatient(Patient patient) {
    this.patient = patient;
    if (patient != null && !patient.getAppointments().contains(this)) {
      patient.addAppointment(this);
    }
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
    if (doctor != null && !doctor.getAppointments().contains(this)) {
      doctor.addAppointment(this);
    }
  }

  public void setAssistant(Assistant assistant) {
    this.assistant = assistant;
    if (assistant != null && !assistant.getAppointments().contains(this)) {
      assistant.addAppointment(this);
    }
  }
}
