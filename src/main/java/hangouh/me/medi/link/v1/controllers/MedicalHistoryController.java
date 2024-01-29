package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.MedicalHistoryFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.MedicalHistory;
import hangouh.me.medi.link.v1.services.MedicalHistoryService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class MedicalHistoryController {
  private final MedicalHistoryService appointmentService;

  @Autowired
  public MedicalHistoryController(MedicalHistoryService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping("/medical-histories")
  public ResponseEntity<ResponsePageDTO<MedicalHistory>> getAllMedicalHistories(
      MedicalHistoryFilterDTO filterDTO) {
    return ResponseUtil.createResponsePage(appointmentService.getAll(filterDTO), HttpStatus.OK);
  }

  @GetMapping("/medical-histories/{id}")
  public ResponseEntity<ResponseDTO<MedicalHistory>> getMedicalHistoryById(@PathVariable UUID id) {
    MedicalHistory response = appointmentService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/medical-histories")
  public ResponseEntity<ResponseDTO<MedicalHistory>> createMedicalHistory(
      @Valid @RequestBody MedicalHistoryBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        appointmentService.create(appointmentDto), HttpStatus.CREATED);
  }

  @PutMapping("/medical-histories/{id}")
  public ResponseEntity<ResponseDTO<MedicalHistory>> updateMedicalHistory(
      @PathVariable UUID id, @RequestBody MedicalHistoryBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        appointmentService.update(id, appointmentDto), HttpStatus.OK);
  }

  @DeleteMapping("/medical-histories/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteMedicalHistory(@PathVariable UUID id) {
    appointmentService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
