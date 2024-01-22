package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.JwtRequest;

public interface AuthService {
  String login(JwtRequest loginDto);
}
