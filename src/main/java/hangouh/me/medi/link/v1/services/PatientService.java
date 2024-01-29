package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.PatientBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PatientFilterDTO;
import hangouh.me.medi.link.v1.models.Patient;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface PatientService {
  /**
   * Get list of patients with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Patient> getAll(PatientFilterDTO filters);

  /**
   * Get a patient by id
   *
   * @param id Patient id
   * @return Patient
   * @throws jakarta.persistence.EntityNotFoundException When patient not found
   */
  Patient getById(UUID id);

  /**
   * Create patient by DTO
   *
   * @param patient data
   * @return Patient
   */
  Patient create(PatientBodyDTO patient);

  /**
   * Update patient by DTO and id
   *
   * @param id patient id
   * @param patient data
   * @return Patient
   * @throws jakarta.persistence.EntityNotFoundException When patient not found
   */
  Patient update(UUID id, PatientBodyDTO patient);

  /**
   * Delete patient by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When patient not found
   */
  void delete(UUID id);
}
