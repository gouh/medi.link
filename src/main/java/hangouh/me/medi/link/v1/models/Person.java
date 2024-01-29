package hangouh.me.medi.link.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class Person {
  @Column(name = "name")
  private String name;

  @Column(name = "contact_info")
  private String contactInfo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties(value = {"password"})
  protected User user;
}
