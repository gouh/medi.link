package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.PrescriptionBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PrescriptionFilterDTO;
import hangouh.me.medi.link.v1.models.Prescription;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface PrescriptionService {
  /**
   * Get list of prescriptions with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Prescription> getAll(PrescriptionFilterDTO filters);

  /**
   * Get prescription by id
   *
   * @param id Prescription id
   * @return Prescription
   * @throws jakarta.persistence.EntityNotFoundException When prescription not found
   */
  Prescription getById(UUID id);

  /**
   * Create prescription by DTO
   *
   * @param prescription data
   * @return Prescription
   */
  Prescription create(PrescriptionBodyDTO prescription);

  /**
   * Update prescription by DTO and id
   *
   * @param id prescription id
   * @param prescription data
   * @return Prescription
   * @throws jakarta.persistence.EntityNotFoundException When prescription not found
   */
  Prescription update(UUID id, PrescriptionBodyDTO prescription);

  /**
   * Delete prescription by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When prescription not found
   */
  void delete(UUID id);
}
