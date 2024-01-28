package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBodyDTO {
  @NotNull @NotEmpty
  @Size(max = 255)
  private String username;

  @NotNull @NotEmpty
  @Size(max = 255)
  private String email;

  @NotNull @NotEmpty
  @Size(max = 255)
  private String password;

  private List<@Size(min = 1, max = 30) String> roles;

  public User toUser(List<Role> roles) {
    User user = new User();
    user.setUsername(this.username);
    user.setEmail(this.email);
    user.setPassword(this.password);
    user.setRoles(roles);
    return user;
  }
}
