package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.PrescriptionBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PrescriptionFilterDTO;
import hangouh.me.medi.link.v1.models.*;
import hangouh.me.medi.link.v1.repositories.*;
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
public class PrescriptionServiceImpl implements PrescriptionService {
  private static final String NOT_FOUND = "Prescription not found with id: %s";
  private static final String ASSOCIATION_NOT_FOUND = "Doctor or patient not found";
  private final PrescriptionRepository prescriptionRepository;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;

  @Autowired
  public PrescriptionServiceImpl(
      PrescriptionRepository prescriptionRepository,
      PatientRepository patientRepository,
      DoctorRepository doctorRepository) {
    this.prescriptionRepository = prescriptionRepository;
    this.patientRepository = patientRepository;
    this.doctorRepository = doctorRepository;
  }

  public Page<Prescription> getAll(PrescriptionFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return prescriptionRepository.findByFilters(filters, pageable);
  }

  public Prescription getById(UUID id) {
    return prescriptionRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public Prescription create(PrescriptionBodyDTO prescription) {
    Optional<Patient> patient = patientRepository.findById(prescription.getPatientId());
    Optional<Doctor> doctor = doctorRepository.findById(prescription.getDoctorId());
    if (patient.isPresent() && doctor.isPresent()) {
      Prescription newPrescription = prescription.toPrescription(patient.get(), doctor.get());
      return prescriptionRepository.save(newPrescription);
    } else {
      throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
    }
  }

  public Prescription update(UUID id, PrescriptionBodyDTO prescription) {
    return prescriptionRepository
        .findById(id)
        .map(
            updatedPrescription -> {
              Optional<Patient> patient = patientRepository.findById(prescription.getPatientId());
              Optional<Doctor> doctor = doctorRepository.findById(prescription.getDoctorId());
              if (patient.isPresent() && doctor.isPresent()) {
                updatedPrescription.setPatient(patient.get());
                updatedPrescription.setDoctor(doctor.get());
                updatedPrescription.setDuration(prescription.getDuration());
                updatedPrescription.setPrescribedMedication(prescription.getPrescribedMedication());
                updatedPrescription.setDosage(prescription.getDosage());
                return prescriptionRepository.save(updatedPrescription);
              }
              throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (prescriptionRepository.existsById(id)) {
      prescriptionRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
