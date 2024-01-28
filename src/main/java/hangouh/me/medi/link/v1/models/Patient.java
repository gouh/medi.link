package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "patient")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "patientId")
public class Patient extends Person {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "patient_id")
  private UUID patientId;

  @Column(name = "date_of_birth")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dob;

  @Column(name = "gender")
  private char gender;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
  private MedicalHistory medicalHistory;

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Prescription> prescriptions = new ArrayList<>();

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Appointment> appointments = new ArrayList<>();

  public void setMedicalHistory(MedicalHistory medicalHistory) {
    this.medicalHistory = medicalHistory;
    if (medicalHistory != null && medicalHistory.getPatient() != this) {
      medicalHistory.setPatient(this);
    }
  }

  public void addPrescription(Prescription prescription) {
    prescriptions.add(prescription);
    if (prescription != null && prescription.getPatient() != this) {
      prescription.setPatient(this);
    }
  }

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
    if (appointment.getPatient() != this) {
      appointment.setPatient(this);
    }
  }

  public void setUser(User user) {
    this.user = user;
    if (user != null && user.getPatient() != this) {
      user.setPatient(this);
    }
  }
}
