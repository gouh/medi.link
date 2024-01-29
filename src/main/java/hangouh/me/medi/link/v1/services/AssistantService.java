package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.AssistantBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AssistantFilterDTO;
import hangouh.me.medi.link.v1.models.Assistant;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface AssistantService {
  /**
   * Get list of assistants with filters using criteria
   *
   * @param filters criteria
   * @return Pagination
   */
  Page<Assistant> getAll(AssistantFilterDTO filters);

  /**
   * Get assistant by id
   *
   * @param id Assistant id
   * @return Assistant
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  Assistant getById(UUID id);

  /**
   * Create assistant by DTO
   *
   * @param assistant data
   * @return Assistant
   */
  Assistant create(AssistantBodyDTO assistant);

  /**
   * Update assistant by DTO and id
   *
   * @param id assistant id
   * @param assistant data
   * @return Assistant
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  Assistant update(UUID id, AssistantBodyDTO assistant);

  /**
   * Delete assistant by id
   *
   * @param id data
   * @throws jakarta.persistence.EntityNotFoundException When assistant not found
   */
  void delete(UUID id);
}
