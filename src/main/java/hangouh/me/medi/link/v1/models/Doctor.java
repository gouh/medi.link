package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "doctor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "doctorId")
public class Doctor extends Person {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "doctor_id")
  private UUID doctorId;

  @Column(name = "specialty")
  private String specialty;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private Date updatedAt;

  @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Prescription> prescriptions = new ArrayList<>();

  @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Appointment> appointments = new ArrayList<>();

  public void addPrescription(Prescription prescription) {
    prescriptions.add(prescription);
    if (prescription.getDoctor() != this) {
      prescription.setDoctor(this);
    }
  }

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
    if (appointment.getDoctor() != this) {
      appointment.setDoctor(this);
    }
  }

  public void setUser(User user) {
    this.user = user;
    if (user != null && user.getDoctor() != this) {
      user.setDoctor(this);
    }
  }
}
