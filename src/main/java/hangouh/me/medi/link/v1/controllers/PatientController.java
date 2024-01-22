package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.PatientBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PatientFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.repositories.PatientRepository;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.validation.Valid;
import java.util.*;
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
public class PatientController {
  public static final String PATIENT_NOT_FOUND = "Patient not found";
  private final PatientRepository patientRepository;

  @Autowired
  public PatientController(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @GetMapping("/patients")
  public ResponseEntity<ResponsePageDTO<Patient>> getAll(@Valid PatientFilterDTO dto) {
    Sort sort = RequestUtil.buildSort(dto.getSortBy());
    Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), sort);
    Page<Patient> patients = patientRepository.findByFilters(dto, pageable);
    return ResponseEntity.ok(new ResponsePageDTO<>(HttpStatus.OK, "", patients));
  }

  @GetMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> getById(@PathVariable UUID id) {
    ResponseDTO<Patient> response =
        patientRepository
            .findById(id)
            .map(patientObj -> new ResponseDTO<>(HttpStatus.OK, "", patientObj))
            .orElseGet(() -> new ResponseDTO<>(HttpStatus.NOT_FOUND, PATIENT_NOT_FOUND, null));

    return ResponseEntity.status(response.getMetadata().getStatusCode()).body(response);
  }

  @PostMapping("/patients")
  public ResponseEntity<ResponseDTO<Patient>> create(@RequestBody PatientBodyDTO patientDTO) {
    Patient patient = patientDTO.toPatient();
    this.patientRepository.save(patient);
    ResponseDTO<Patient> response = new ResponseDTO<>(HttpStatus.CREATED, "", patient);
    return ResponseEntity.status(response.getMetadata().getStatusCode()).body(response);
  }

  @PutMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> update(
      @PathVariable UUID id, @RequestBody PatientBodyDTO patientDTO) {
    ResponseDTO<Patient> response =
        new ResponseDTO<>(HttpStatus.NOT_FOUND, PATIENT_NOT_FOUND, null);
    Optional<Patient> patientOptional = this.patientRepository.findById(id);
    if (patientOptional.isPresent()) {
      Patient patient = patientOptional.get();
      patient.setName(patientDTO.getName());
      patient.setDob(patientDTO.getDob());
      patient.setGender(patientDTO.getGender());
      patient.setContactInfo(patientDTO.getContactInfo());
      this.patientRepository.save(patient);
      response = new ResponseDTO<>(HttpStatus.OK, "", patient);
    }
    return ResponseEntity.status(response.getMetadata().getStatusCode()).body(response);
  }

  @DeleteMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> delete(@PathVariable UUID id) {
    ResponseDTO<Patient> response =
        new ResponseDTO<>(HttpStatus.NOT_FOUND, PATIENT_NOT_FOUND, null);
    if (this.patientRepository.existsById(id)) {
      this.patientRepository.deleteById(id);
      response = new ResponseDTO<>(HttpStatus.NO_CONTENT, "", null);
    }
    return ResponseEntity.status(response.getMetadata().getStatusCode()).body(response);
  }
}
