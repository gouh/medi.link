package hangouh.me.medi.link.v1.repository;

import hangouh.me.medi.link.v1.DTO.requests.PatientFilterDTO;
import hangouh.me.medi.link.v1.models.Patient;
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
public interface PatientRepository
    extends JpaRepository<Patient, UUID>, JpaSpecificationExecutor<Patient> {
  default Page<Patient> findByFilters(PatientFilterDTO dto, Pageable pageable) {
    Specification<Patient> spec =
        (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (dto.getPatientId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("patientId"), dto.getPatientId()));
          }
          if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
          }
          if (dto.getDateOfBirth() != null) {
            predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), dto.getDateOfBirth()));
          }
          if (dto.getGender() != null) {
            predicates.add(
                criteriaBuilder.like(
                    root.get("gender").as(String.class), "%" + dto.getGender() + "%"));
          }
          if (dto.getContactInfo() != null && !dto.getContactInfo().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("contactInfo"), "%" + dto.getContactInfo() + "%"));
          }

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    return findAll(spec, pageable);
  }
}
