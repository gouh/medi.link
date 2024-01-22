package hangouh.me.medi.link.v1.repositories;

import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.models.Doctor;
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
public interface DoctorRepository
    extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {
  default Page<Doctor> findByFilters(DoctorFilterDTO dto, Pageable pageable) {
    Specification<Doctor> spec =
        (root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (dto.getDoctorId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("doctorId"), dto.getDoctorId()));
          }
          if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
          }
          if (dto.getSpecialty() != null && !dto.getSpecialty().trim().isEmpty()) {
            predicates.add(
                criteriaBuilder.like(root.get("specialty"), "%" + dto.getSpecialty() + "%"));
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
