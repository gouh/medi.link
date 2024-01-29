package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.security.JwtTokenProvider;
import hangouh.me.medi.link.v1.DTO.requests.JwtRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthService implements AuthService {
  private AuthenticationManager authenticationManager;
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public String login(JwtRequest loginDto) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtTokenProvider.generateToken(authentication);
  }
}
