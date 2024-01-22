package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.models.User;
import hangouh.me.medi.link.v1.repositories.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not exists by Username or Email"));

    Set<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map((role) -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(
        usernameOrEmail, user.getPassword(), authorities);
  }
}
