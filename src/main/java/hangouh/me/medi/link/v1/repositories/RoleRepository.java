package hangouh.me.medi.link.v1.repositories;

import hangouh.me.medi.link.v1.models.Role;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  List<Role> findAllByNameIn(List<String> names);

  Role findByName(String name);
}
