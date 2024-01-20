package hangouh.me.medi.link.v1.controllers;

import java.sql.Connection;
import java.util.Collections;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@V1ApiController
public class HealthController {
  private final DataSource dataSource;

  public HealthController(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> checkDatabaseHealth() {
    try (Connection connection = dataSource.getConnection()) {
      if (connection.isValid(1)) {
        return ResponseEntity.ok(Collections.singletonMap("data", "OK"));
      } else {
        return ResponseEntity.status(503).body(Collections.singletonMap("data", "Error"));
      }
    } catch (Exception e) {
      return ResponseEntity.status(503).body(Collections.singletonMap("data", "Error"));
    }
  }
}
