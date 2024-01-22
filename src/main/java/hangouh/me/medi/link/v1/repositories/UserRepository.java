package hangouh.me.medi.link.v1.repositories;

import hangouh.me.medi.link.v1.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsernameOrEmail(String username, String email);
}
