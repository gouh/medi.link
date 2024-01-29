package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.AssistantBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AssistantFilterDTO;
import hangouh.me.medi.link.v1.models.Assistant;
import hangouh.me.medi.link.v1.repositories.AssistantRepository;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AssistantServiceImpl implements AssistantService {
  private static final String NOT_FOUND = "Assistant not found with id: %s";
  private final AssistantRepository assistantRepository;
  private final UserService userService;

  @Autowired
  public AssistantServiceImpl(AssistantRepository assistantRepository, UserService userService) {
    this.assistantRepository = assistantRepository;
    this.userService = userService;
  }

  public Page<Assistant> getAll(AssistantFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return assistantRepository.findByFilters(filters, pageable);
  }

  public Assistant getById(UUID id) {
    return assistantRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public Assistant create(AssistantBodyDTO patient) {
    Assistant newAssistant = patient.toAssistant();
    userService.updateUserForPerson(newAssistant, patient.getUser());
    return assistantRepository.save(newAssistant);
  }

  public Assistant update(UUID id, AssistantBodyDTO patient) {
    return assistantRepository
        .findById(id)
        .map(
            updatedPatient -> {
              updatedPatient.setName(patient.getName());
              updatedPatient.setContactInfo(patient.getContactInfo());
              userService.updateUserForPerson(updatedPatient, patient.getUser());
              return assistantRepository.save(updatedPatient);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (assistantRepository.existsById(id)) {
      assistantRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
