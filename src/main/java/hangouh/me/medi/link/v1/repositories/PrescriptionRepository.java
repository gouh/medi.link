package hangouh.me.medi.link.v1.repositories;

import hangouh.me.medi.link.v1.DTO.requests.PrescriptionFilterDTO;
import hangouh.me.medi.link.v1.models.Prescription;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrescriptionRepository
    extends JpaRepository<Prescription, UUID>, JpaSpecificationExecutor<Prescription> {
  default Page<Prescription> findByFilters(PrescriptionFilterDTO filterDTO, Pageable pageable) {
    return findAll(
        (Specification<Prescription>)
            (root, query, criteriaBuilder) -> {
              List<Predicate> predicates = new ArrayList<>();
              if (filterDTO.getPatientId() != null) {
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("patient").get("patientId"), filterDTO.getPatientId()));
              }
              if (filterDTO.getDoctorId() != null) {
                predicates.add(
                    criteriaBuilder.equal(
                        root.get("doctor").get("doctorId"), filterDTO.getDoctorId()));
              }
              if (filterDTO.getPrescribedMedication() != null
                  && !filterDTO.getPrescribedMedication().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("prescribed_medication"),
                        "%" + filterDTO.getPrescribedMedication() + "%"));
              }
              if (filterDTO.getDosage() != null && !filterDTO.getDosage().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(root.get("dosage"), "%" + filterDTO.getDosage() + "%"));
              }
              if (filterDTO.getDuration() != null && !filterDTO.getDuration().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("duration"), "%" + filterDTO.getDuration() + "%"));
              }
              return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            },
        pageable);
  }
}
