package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.DoctorBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.models.Doctor;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface DoctorService {
  /**
   * Get list of doctors with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Doctor> getAll(DoctorFilterDTO filters);

  /**
   * Get doctor by id
   *
   * @param id Doctor id
   * @return Doctor
   * @throws jakarta.persistence.EntityNotFoundException When doctor not found
   */
  Doctor getById(UUID id);

  /**
   * Create doctor by DTO
   *
   * @param doctor data
   * @return Doctor
   */
  Doctor create(DoctorBodyDTO doctor);

  /**
   * Update doctor by DTO and id
   *
   * @param id doctor id
   * @param doctor data
   * @return Doctor
   * @throws jakarta.persistence.EntityNotFoundException When doctor not found
   */
  Doctor update(UUID id, DoctorBodyDTO doctor);

  /**
   * Delete doctor by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When doctor not found
   */
  void delete(UUID id);
}
