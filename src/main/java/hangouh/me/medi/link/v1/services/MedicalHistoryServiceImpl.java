package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryFilterDTO;
import hangouh.me.medi.link.v1.models.*;
import hangouh.me.medi.link.v1.repositories.MedicalHistoryRepository;
import hangouh.me.medi.link.v1.repositories.PatientRepository;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
  private static final String NOT_FOUND = "MedicalHistory not found with id: %s";
  private static final String ASSOCIATION_NOT_FOUND = "Patient not found";
  private final MedicalHistoryRepository medicalHistoryRepository;
  private final PatientRepository patientRepository;

  @Autowired
  public MedicalHistoryServiceImpl(
      MedicalHistoryRepository medicalHistoryRepository, PatientRepository patientRepository) {
    this.medicalHistoryRepository = medicalHistoryRepository;
    this.patientRepository = patientRepository;
  }

  public Page<MedicalHistory> getAll(MedicalHistoryFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return medicalHistoryRepository.findByFilters(filters, pageable);
  }

  public MedicalHistory getById(UUID id) {
    return medicalHistoryRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public MedicalHistory create(MedicalHistoryBodyDTO medicalHistory) {
    Optional<Patient> patient = patientRepository.findById(medicalHistory.getPatientId());
    if (patient.isPresent()) {
      MedicalHistory newMedicalHistory = medicalHistory.toMedicalHistory(patient.get());
      return medicalHistoryRepository.save(newMedicalHistory);
    } else {
      throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
    }
  }

  public MedicalHistory update(UUID id, MedicalHistoryBodyDTO medicalHistory) {
    return medicalHistoryRepository
        .findById(id)
        .map(
            updatedMedicalHistory -> {
              Optional<Patient> patient = patientRepository.findById(medicalHistory.getPatientId());
              if (patient.isPresent()) {
                updatedMedicalHistory.setPatient(patient.get());
                updatedMedicalHistory.setDiseaseHistory(medicalHistory.getDiseaseHistory());
                updatedMedicalHistory.setAllergies(medicalHistory.getAllergies());
                updatedMedicalHistory.setPreviousSurgeries(medicalHistory.getPreviousSurgeries());
                return medicalHistoryRepository.save(updatedMedicalHistory);
              }
              throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (medicalHistoryRepository.existsById(id)) {
      medicalHistoryRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
