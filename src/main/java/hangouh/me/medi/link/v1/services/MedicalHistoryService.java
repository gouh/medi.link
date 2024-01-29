package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryFilterDTO;
import hangouh.me.medi.link.v1.models.MedicalHistory;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface MedicalHistoryService {
  /**
   * Get list of medical histories with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<MedicalHistory> getAll(MedicalHistoryFilterDTO filters);

  /**
   * Get medicalHistory by id
   *
   * @param id MedicalHistory id
   * @return MedicalHistory
   * @throws jakarta.persistence.EntityNotFoundException When medicalHistory not found
   */
  MedicalHistory getById(UUID id);

  /**
   * Create medicalHistory by DTO
   *
   * @param medicalHistory data
   * @return MedicalHistory
   */
  MedicalHistory create(MedicalHistoryBodyDTO medicalHistory);

  /**
   * Update medicalHistory by DTO and id
   *
   * @param id medicalHistory id
   * @param medicalHistory data
   * @return MedicalHistory
   * @throws jakarta.persistence.EntityNotFoundException When medicalHistory not found
   */
  MedicalHistory update(UUID id, MedicalHistoryBodyDTO medicalHistory);

  /**
   * Delete medicalHistory by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When medicalHistory not found
   */
  void delete(UUID id);
}
