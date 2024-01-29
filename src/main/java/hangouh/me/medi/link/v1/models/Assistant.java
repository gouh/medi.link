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
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "assistant")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "assistantId")
public class Assistant extends Person {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "assistant_id")
  private UUID assistantId;

  @OneToMany(mappedBy = "assistant", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Appointment> appointments = new ArrayList<>();

  public void addAppointment(Appointment appointment) {
    appointments.add(appointment);
    if (appointment.getAssistant() != this) {
      appointment.setAssistant(this);
    }
  }

  public void setUser(User user) {
    this.user = user;
    if (user != null && user.getAssistant() != this) {
      user.setAssistant(this);
    }
  }
}
