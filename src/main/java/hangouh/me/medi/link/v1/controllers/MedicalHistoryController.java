package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.MedicalHistory;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.repositories.MedicalHistoryRepository;
import hangouh.me.medi.link.v1.repositories.PatientRepository;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class MedicalHistoryController {
  public static final String MEDICAL_HISTORY_NOT_FOUND = "Medical history not found";
  private final MedicalHistoryRepository medicalHistoryRepository;
  private final PatientRepository patientRepository;

  @Autowired
  public MedicalHistoryController(
      MedicalHistoryRepository medicalHistoryRepository, PatientRepository patientRepository) {
    this.medicalHistoryRepository = medicalHistoryRepository;
    this.patientRepository = patientRepository;
  }

  @GetMapping("/medical-histories")
  public ResponseEntity<ResponsePageDTO<MedicalHistory>> getAllMedicalHistories(
      @Valid MedicalHistoryFilterDTO dto) {
    Sort sort = RequestUtil.buildSort(dto.getSortBy());
    Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), sort);
    Page<MedicalHistory> medicalHistories = medicalHistoryRepository.findByFilters(dto, pageable);
    return ResponseEntity.ok(new ResponsePageDTO<>(HttpStatus.OK, "", medicalHistories));
  }

  @GetMapping("/medical-histories/{id}")
  public ResponseEntity<ResponseDTO<MedicalHistory>> getMedicalHistoryById(@PathVariable UUID id) {
    return medicalHistoryRepository
        .findById(id)
        .map(
            medicalHistory ->
                ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "", medicalHistory)))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        new ResponseDTO<>(HttpStatus.NOT_FOUND, MEDICAL_HISTORY_NOT_FOUND, null)));
  }

  @PostMapping("/medical-histories")
  public ResponseEntity<ResponseDTO<MedicalHistory>> createMedicalHistory(
      @Valid @RequestBody MedicalHistoryBodyDTO dto) {
    Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
    if (patient.isPresent()) {
      if (patient.get().getMedicalHistory() != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST, "Medical history already exist", null));
      }
      MedicalHistory medicalHistory = dto.toMedicalHistory(patient.get());
      MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(medicalHistory);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ResponseDTO<>(HttpStatus.CREATED, "", savedMedicalHistory));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseDTO<>(HttpStatus.NOT_FOUND, PatientController.PATIENT_NOT_FOUND, null));
    }
  }

  @PutMapping("/medical-histories/{id}")
  public ResponseEntity<ResponseDTO<MedicalHistory>> updateMedicalHistory(
      @PathVariable UUID id, @Valid @RequestBody MedicalHistoryBodyDTO dto) {
    Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
    return patient
        .map(
            value ->
                medicalHistoryRepository
                    .findById(id)
                    .map(
                        medicalHistory -> {
                          medicalHistory.setPatient(value);
                          medicalHistory.setDiseaseHistory(dto.getDiseaseHistory());
                          medicalHistory.setAllergies(dto.getAllergies());
                          medicalHistory.setPreviousSurgeries(dto.getPreviousSurgeries());
                          MedicalHistory updatedMedicalHistory =
                              medicalHistoryRepository.save(medicalHistory);
                          return ResponseEntity.ok(
                              new ResponseDTO<>(HttpStatus.OK, "", updatedMedicalHistory));
                        })
                    .orElseGet(
                        () ->
                            ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(
                                    new ResponseDTO<>(
                                        HttpStatus.NOT_FOUND, MEDICAL_HISTORY_NOT_FOUND, null))))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        new ResponseDTO<>(
                            HttpStatus.NOT_FOUND, PatientController.PATIENT_NOT_FOUND, null)));
  }

  @DeleteMapping("/medical-histories/{id}")
  @Transactional
  public ResponseEntity<ResponseDTO<Void>> deleteMedicalHistory(@PathVariable UUID id) {
    Optional<MedicalHistory> medicalHistoryOpt = medicalHistoryRepository.findById(id);
    if (medicalHistoryOpt.isPresent()) {
      MedicalHistory medicalHistory = medicalHistoryOpt.get();
      Patient patient = medicalHistory.getPatient();
      if (patient != null) {
        patient.setMedicalHistory(null);
        patientRepository.save(patient);
      }
      medicalHistoryRepository.delete(medicalHistory);
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(new ResponseDTO<>(HttpStatus.NO_CONTENT, "", null));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseDTO<>(HttpStatus.NOT_FOUND, MEDICAL_HISTORY_NOT_FOUND, null));
    }
  }
}
