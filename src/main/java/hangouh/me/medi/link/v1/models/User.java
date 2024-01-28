package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id")
  private UUID userId;

  @Column(nullable = false, unique = true, name = "username")
  private String username;

  @Column(nullable = false, unique = true, name = "email")
  private String email;

  @Column(nullable = false, name = "password")
  @JsonIgnore
  private String password;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
  @JsonIgnore
  private List<Role> roles;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Patient patient;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Doctor doctor;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Assistant assistant;

  public void setPatient(Patient patient) {
    this.patient = patient;
    if (patient != null && patient.getUser() != this) {
      patient.setUser(this);
    }
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
    if (doctor != null && doctor.getUser() != this) {
      doctor.setUser(this);
    }
  }

  public void setAssistant(Assistant assistant) {
    this.assistant = assistant;
    if (assistant != null && assistant.getUser() != this) {
      assistant.setUser(this);
    }
  }
}
