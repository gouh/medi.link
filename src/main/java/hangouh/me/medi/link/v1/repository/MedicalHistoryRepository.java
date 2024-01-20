package hangouh.me.medi.link.v1.repository;

import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryFilterDTO;
import hangouh.me.medi.link.v1.models.MedicalHistory;
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
public interface MedicalHistoryRepository
    extends JpaRepository<MedicalHistory, UUID>, JpaSpecificationExecutor<MedicalHistory> {
  default Page<MedicalHistory> findByFilters(MedicalHistoryFilterDTO filterDTO, Pageable pageable) {
    return findAll(
        (Specification<MedicalHistory>)
            (root, query, criteriaBuilder) -> {
              List<Predicate> predicates = new ArrayList<>();

              if (filterDTO.getDiseaseHistory() != null
                  && !filterDTO.getDiseaseHistory().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("diseaseHistory"), "%" + filterDTO.getDiseaseHistory() + "%"));
              }
              if (filterDTO.getAllergies() != null && !filterDTO.getAllergies().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("allergies"), "%" + filterDTO.getAllergies() + "%"));
              }
              if (filterDTO.getPreviousSurgeries() != null
                  && !filterDTO.getPreviousSurgeries().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("previousSurgeries"),
                        "%" + filterDTO.getPreviousSurgeries() + "%"));
              }

              return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            },
        pageable);
  }
}
