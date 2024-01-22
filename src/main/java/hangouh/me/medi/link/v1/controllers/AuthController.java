package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.JwtRequest;
import hangouh.me.medi.link.v1.DTO.requests.RegisterBodyDTO;
import hangouh.me.medi.link.v1.DTO.responses.JwtResponse;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.models.Role;
import hangouh.me.medi.link.v1.models.User;
import hangouh.me.medi.link.v1.repositories.RoleRepository;
import hangouh.me.medi.link.v1.repositories.UserRepository;
import hangouh.me.medi.link.v1.services.AuthService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthController {
  private AuthService authService;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  @PostMapping("/auth/login")
  public ResponseEntity<ResponseDTO<JwtResponse>> login(@RequestBody JwtRequest dto) {
    String token = authService.login(dto);
    JwtResponse jwtAuthResponse = new JwtResponse();
    jwtAuthResponse.setAccessToken(token);
    return new ResponseEntity<>(
        new ResponseDTO<>(HttpStatus.OK, "", jwtAuthResponse), HttpStatus.OK);
  }

  @PostMapping("/auth/register")
  public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody RegisterBodyDTO dto) {
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    List<Role> roles = roleRepository.findAllByNameIn(dto.getRoles());
    User user = dto.toUser(roles);
    userRepository.save(user);
    return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "", user), HttpStatus.OK);
  }
}
