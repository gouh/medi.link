package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.DoctorBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.models.Doctor;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface DoctorService {
  /**
   * Get list of assistants with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Doctor> getAll(DoctorFilterDTO filters);

  /**
   * Get assistant by id
   *
   * @param id Doctor id
   * @return Doctor
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  Doctor getById(UUID id);

  /**
   * Create assistant by DTO
   *
   * @param assistant data
   * @return Doctor
   */
  Doctor create(DoctorBodyDTO assistant);

  /**
   * Update assistant by DTO and id
   *
   * @param id assistant id
   * @param assistant data
   * @return Doctor
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  Doctor update(UUID id, DoctorBodyDTO assistant);

  /**
   * Delete assistant by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  void delete(UUID id);
}
