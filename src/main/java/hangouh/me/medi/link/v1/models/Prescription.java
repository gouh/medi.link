package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "prescription")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "prescriptionId")
public class Prescription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "prescription_id")
  private UUID prescriptionId;

  @JoinColumn(name = "patient_id")
  @ManyToOne()
  @JsonBackReference
  private Patient patient;

  @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
  @ManyToOne(fetch = FetchType.EAGER)
  @JsonBackReference
  private Doctor doctor;

  @Column(name = "prescribed_medication")
  private String prescribedMedication;

  @Column(name = "dosage")
  private String dosage;

  @Column(name = "duration")
  private String duration;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  public void setPatient(Patient patient) {
    this.patient = patient;
    if (patient != null && !patient.getPrescriptions().contains(this)) {
      patient.addPrescription(this);
    }
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
    if (doctor != null && !doctor.getPrescriptions().contains(this)) {
      doctor.addPrescription(this);
    }
  }
}
