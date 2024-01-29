package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.AppointmentBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AppointmentFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Appointment;
import hangouh.me.medi.link.v1.services.AppointmentService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class AppointmentController {
  private final AppointmentService appointmentService;

  @Autowired
  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping("/appointments")
  public ResponseEntity<ResponsePageDTO<Appointment>> getAllAppointments(
      AppointmentFilterDTO filterDTO) {
    return ResponseUtil.createResponsePage(appointmentService.getAll(filterDTO), HttpStatus.OK);
  }

  @GetMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Appointment>> getAppointmentById(@PathVariable UUID id) {
    Appointment response = appointmentService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/appointments")
  public ResponseEntity<ResponseDTO<Appointment>> createAppointment(
      @Valid @RequestBody AppointmentBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        appointmentService.create(appointmentDto), HttpStatus.CREATED);
  }

  @PutMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Appointment>> updateAppointment(
      @PathVariable UUID id, @RequestBody AppointmentBodyDTO appointmentDto) {
    return ResponseUtil.createResponseEntity(
        appointmentService.update(id, appointmentDto), HttpStatus.OK);
  }

  @DeleteMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteAppointment(@PathVariable UUID id) {
    appointmentService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
