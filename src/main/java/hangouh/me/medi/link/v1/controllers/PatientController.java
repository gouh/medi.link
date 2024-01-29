package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.PatientBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PatientFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.services.PatientService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class PatientController {
  private final PatientService patientService;

  @Autowired
  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping("/patients")
  public ResponseEntity<ResponsePageDTO<Patient>> getAll(@Valid PatientFilterDTO dto) {
    return ResponseUtil.createResponsePage(patientService.getAll(dto), HttpStatus.OK);
  }

  @GetMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> getById(@PathVariable UUID id) {
    Patient response = patientService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/patients")
  public ResponseEntity<ResponseDTO<Patient>> create(
      @Valid @RequestBody PatientBodyDTO patientDTO) {
    return ResponseUtil.createResponseEntity(patientService.create(patientDTO), HttpStatus.CREATED);
  }

  @PutMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> update(
      @PathVariable UUID id, @Valid @RequestBody PatientBodyDTO patientDTO) {
    return ResponseUtil.createResponseEntity(patientService.update(id, patientDTO), HttpStatus.OK);
  }

  @DeleteMapping("/patients/{id}")
  public ResponseEntity<ResponseDTO<Patient>> delete(@PathVariable UUID id) {
    this.patientService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
