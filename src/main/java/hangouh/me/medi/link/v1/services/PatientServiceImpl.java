package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.PatientBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PatientFilterDTO;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.repositories.PatientRepository;
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
public class PatientServiceImpl implements PatientService {
  private static final String NOT_FOUND = "Patient not found with id: %s";
  private final PatientRepository patientRepository;
  private final UserService userService;

  @Autowired
  public PatientServiceImpl(PatientRepository patientRepository, UserService userService) {
    this.patientRepository = patientRepository;
    this.userService = userService;
  }

  public Page<Patient> getAll(PatientFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return patientRepository.findByFilters(filters, pageable);
  }

  public Patient getById(UUID id) {
    return patientRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public Patient create(PatientBodyDTO patient) {
    Patient newPatient = patient.toPatient();
    userService.updateUserForPerson(newPatient, patient.getUser());
    return patientRepository.save(newPatient);
  }

  public Patient update(UUID id, PatientBodyDTO patient) {
    return patientRepository
        .findById(id)
        .map(
            updatedPatient -> {
              updatedPatient.setName(patient.getName());
              updatedPatient.setDob(patient.getDob());
              updatedPatient.setGender(patient.getGender());
              updatedPatient.setContactInfo(patient.getContactInfo());
              userService.updateUserForPerson(updatedPatient, patient.getUser());
              return patientRepository.save(updatedPatient);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (patientRepository.existsById(id)) {
      patientRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
