package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.AppointmentBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AppointmentFilterDTO;
import hangouh.me.medi.link.v1.models.Appointment;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface AppointmentService {
  /**
   * Get list of appointments with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Appointment> getAll(AppointmentFilterDTO filters);

  /**
   * Get appointment by id
   *
   * @param id Appointment id
   * @return Appointment
   * @throws jakarta.persistence.EntityNotFoundException When appointment not found
   */
  Appointment getById(UUID id);

  /**
   * Create appointment by DTO
   *
   * @param appointment data
   * @return Appointment
   */
  Appointment create(AppointmentBodyDTO appointment);

  /**
   * Update appointment by DTO and id
   *
   * @param id appointment id
   * @param appointment data
   * @return Appointment
   * @throws jakarta.persistence.EntityNotFoundException When appointment not found
   */
  Appointment update(UUID id, AppointmentBodyDTO appointment);

  /**
   * Delete appointment by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When appointment not found
   */
  void delete(UUID id);
}
