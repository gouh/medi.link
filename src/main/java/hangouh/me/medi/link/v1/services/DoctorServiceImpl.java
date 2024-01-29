package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.DoctorBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.repositories.DoctorRepository;
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
public class DoctorServiceImpl implements DoctorService {
  private static final String NOT_FOUND = "Doctor not found with id: %s";
  private final DoctorRepository doctorRepository;
  private final UserService userService;

  @Autowired
  public DoctorServiceImpl(DoctorRepository patientRepository, UserService userService) {
    this.doctorRepository = patientRepository;
    this.userService = userService;
  }

  public Page<Doctor> getAll(DoctorFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return doctorRepository.findByFilters(filters, pageable);
  }

  public Doctor getById(UUID id) {
    return doctorRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public Doctor create(DoctorBodyDTO patient) {
    Doctor newDoctor = patient.toDoctor();
    userService.updateUserForPerson(newDoctor, patient.getUser());
    return doctorRepository.save(newDoctor);
  }

  public Doctor update(UUID id, DoctorBodyDTO doctor) {
    return doctorRepository
        .findById(id)
        .map(
            updatedDoctor -> {
              updatedDoctor.setName(doctor.getName());
              updatedDoctor.setContactInfo(doctor.getContactInfo());
              updatedDoctor.setSpecialty(doctor.getSpecialty());
              userService.updateUserForPerson(updatedDoctor, doctor.getUser());
              return doctorRepository.save(updatedDoctor);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (doctorRepository.existsById(id)) {
      doctorRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
