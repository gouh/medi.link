package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.JwtRequest;
import hangouh.me.medi.link.v1.DTO.responses.JwtResponse;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthController {
  private AuthService authService;

  @PostMapping("/auth/login")
  public ResponseEntity<ResponseDTO<JwtResponse>> login(@RequestBody JwtRequest dto) {
    String token = authService.login(dto);
    JwtResponse jwtAuthResponse = new JwtResponse();
    jwtAuthResponse.setAccessToken(token);
    return new ResponseEntity<>(
        new ResponseDTO<>(HttpStatus.OK, "", jwtAuthResponse), HttpStatus.OK);
  }
}
