package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "assistant")
public class Assistant extends Person {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "assistant_id")
  private UUID assistantId;

  @OneToMany(mappedBy = "assistant", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Appointment> appointments = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

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
