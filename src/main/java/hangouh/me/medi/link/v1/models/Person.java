package hangouh.me.medi.link.v1.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
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
}
