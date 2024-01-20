package hangouh.me.medi.link.v1.repository;

import hangouh.me.medi.link.v1.DTO.requests.AppointmentFilterDTO;
import hangouh.me.medi.link.v1.models.Appointment;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentRepository
    extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {
  default Page<Appointment> findByFilters(AppointmentFilterDTO filterDTO, Pageable pageable) {
    return findAll(
        (Specification<Appointment>)
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
              if (filterDTO.getConsultationReason() != null
                  && !filterDTO.getConsultationReason().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        root.get("consultationReason"),
                        "%" + filterDTO.getConsultationReason() + "%"));
              }
              if (filterDTO.getFromDate() != null) {
                predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dateTime"), filterDTO.getFromDate()));
              }
              if (filterDTO.getToDate() != null) {
                predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get("dateTime"), filterDTO.getToDate()));
              }

              return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            },
        pageable);
  }
}
