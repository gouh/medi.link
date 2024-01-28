package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.UserBodyDTO;
import hangouh.me.medi.link.v1.models.Role;
import hangouh.me.medi.link.v1.models.User;
import hangouh.me.medi.link.v1.repositories.RoleRepository;
import hangouh.me.medi.link.v1.repositories.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  public User upserUser(@Nullable UserBodyDTO dto, @Nullable UUID userID) {
    if (dto == null) {
      return null;
    }
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    List<Role> roles =
        roleRepository.findAllByNameIn(
            dto.getRoles().stream().map(role -> "ROLE_" + role).collect(Collectors.toList()));
    User userData = dto.toUser(roles);

    if (userID != null) {
      return userRepository
          .findById(userID)
          .map(user -> updateUserWithNewData(user, userData))
          .orElseGet(() -> userRepository.save(userData));
    } else {
      return userRepository.save(userData);
    }
  }

  private User updateUserWithNewData(User existingUser, User newData) {
    existingUser.setUsername(newData.getUsername());
    existingUser.setEmail(newData.getEmail());
    existingUser.setPassword(newData.getPassword());
    existingUser.setRoles(newData.getRoles());
    return userRepository.save(existingUser);
  }
}
