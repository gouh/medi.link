package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "medical_history")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "historyId")
public class MedicalHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "history_id")
  private UUID historyId;

  @Column(name = "disease_history")
  private String diseaseHistory;

  @Column(name = "allergies")
  private String allergies;

  @Column(name = "previous_surgeries")
  private String previousSurgeries;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "patient_id")
  @JsonManagedReference
  @JsonIgnoreProperties(value = {"prescriptions", "appointments", "medicalHistory", "user"})
  private Patient patient;

  public void setPatient(Patient patient) {
    this.patient = patient;
    if (patient != null && patient.getMedicalHistory() != this) {
      patient.setMedicalHistory(this);
    }
  }
}
