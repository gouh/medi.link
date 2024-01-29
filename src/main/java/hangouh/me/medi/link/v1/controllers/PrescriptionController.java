package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.PrescriptionBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.PrescriptionFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Prescription;
import hangouh.me.medi.link.v1.services.PrescriptionService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class PrescriptionController {
  private final PrescriptionService prescriptionService;

  @Autowired
  public PrescriptionController(PrescriptionService prescriptionService) {
    this.prescriptionService = prescriptionService;
  }

  @GetMapping("/prescriptions")
  public ResponseEntity<ResponsePageDTO<Prescription>> getAllPrescriptions(
      PrescriptionFilterDTO filterDTO) {
    return ResponseUtil.createResponsePage(prescriptionService.getAll(filterDTO), HttpStatus.OK);
  }

  @GetMapping("/prescriptions/{id}")
  public ResponseEntity<ResponseDTO<Prescription>> getPrescriptionById(@PathVariable UUID id) {
    Prescription response = prescriptionService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/prescriptions")
  public ResponseEntity<ResponseDTO<Prescription>> createPrescription(
      @Valid @RequestBody PrescriptionBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        prescriptionService.create(appointmentDto), HttpStatus.CREATED);
  }

  @PutMapping("/prescriptions/{id}")
  public ResponseEntity<ResponseDTO<Prescription>> updatePrescription(
      @PathVariable UUID id, @RequestBody PrescriptionBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        prescriptionService.update(id, appointmentDto), HttpStatus.OK);
  }

  @DeleteMapping("/prescriptions/{id}")
  public ResponseEntity<ResponseDTO<Void>> deletePrescription(@PathVariable UUID id) {
    prescriptionService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
