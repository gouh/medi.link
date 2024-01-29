package hangouh.me.medi.link.v1.repositories;

import hangouh.me.medi.link.v1.DTO.requests.AssistantFilterDTO;
import hangouh.me.medi.link.v1.models.Assistant;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository
    extends JpaRepository<Assistant, UUID>, JpaSpecificationExecutor<Assistant> {
  default Page<Assistant> findByFilters(AssistantFilterDTO dto, Pageable pageable) {
    Specification<Assistant> spec =
        (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (dto.getAssistantId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("assistantId"), dto.getAssistantId()));
          }
          if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
          }
          if (dto.getContactInfo() != null && !dto.getContactInfo().trim().isEmpty()) {
            predicates.add(
                criteriaBuilder.like(root.get("contactInfo"), "%" + dto.getContactInfo() + "%"));
          }

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    return findAll(spec, pageable);
  }
}
