package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.AppointmentBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AppointmentFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Appointment;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.repositories.AppointmentRepository;
import hangouh.me.medi.link.v1.repositories.DoctorRepository;
import hangouh.me.medi.link.v1.repositories.PatientRepository;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class AppointmentController {
  public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
  private final AppointmentRepository appointmentRepository;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;

  @Autowired
  public AppointmentController(
      AppointmentRepository appointmentRepository,
      PatientRepository patientRepository,
      DoctorRepository doctorRepository) {
    this.appointmentRepository = appointmentRepository;
    this.patientRepository = patientRepository;
    this.doctorRepository = doctorRepository;
  }

  @GetMapping("/appointments")
  public ResponseEntity<ResponsePageDTO<Appointment>> getAllAppointments(
      AppointmentFilterDTO filterDTO) {
    Sort sort = RequestUtil.buildSort(filterDTO.getSortBy());
    PageRequest pageRequest = PageRequest.of(filterDTO.getPage(), filterDTO.getSize(), sort);
    Page<Appointment> appointmentsPage =
        appointmentRepository.findByFilters(filterDTO, pageRequest);
    ResponsePageDTO<Appointment> response =
        new ResponsePageDTO<>(HttpStatus.OK, "", appointmentsPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Appointment>> getAppointmentById(@PathVariable UUID id) {
    return appointmentRepository
        .findById(id)
        .map(appointment -> ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "", appointment)))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(HttpStatus.NOT_FOUND, APPOINTMENT_NOT_FOUND, null)));
  }

  @PostMapping("/appointments")
  public ResponseEntity<ResponseDTO<Appointment>> createAppointment(
      @Valid @RequestBody AppointmentBodyDTO dto) {
    Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
    Optional<Doctor> doctor = doctorRepository.findById(dto.getDoctorId());

    if (patient.isPresent() && doctor.isPresent()) {
      Appointment newAppointment = dto.toAppointment(patient.get(), doctor.get());
      appointmentRepository.save(newAppointment);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ResponseDTO<>(HttpStatus.CREATED, "", newAppointment));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST, "Invalid patient or doctor ID", null));
    }
  }

  @PutMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Appointment>> updateAppointment(
      @PathVariable UUID id, @RequestBody AppointmentBodyDTO dto) {
    return appointmentRepository
        .findById(id)
        .map(
            appointment -> {
              appointment.setDateTime(dto.getDateTime());
              appointment.setConsultationReason(dto.getConsultationReason());

              Optional<Patient> patient = patientRepository.findById(dto.getPatientId());
              Optional<Doctor> doctor = doctorRepository.findById(dto.getDoctorId());
              patient.ifPresent(appointment::setPatient);
              doctor.ifPresent(appointment::setDoctor);

              Appointment updatedAppointment = appointmentRepository.save(appointment);
              return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "", updatedAppointment));
            })
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(HttpStatus.NOT_FOUND, APPOINTMENT_NOT_FOUND, null)));
  }

  @DeleteMapping("/appointments/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteAppointment(@PathVariable UUID id) {
    if (appointmentRepository.existsById(id)) {
      appointmentRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(new ResponseDTO<>(HttpStatus.NO_CONTENT, "", null));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseDTO<>(HttpStatus.NOT_FOUND, APPOINTMENT_NOT_FOUND, null));
    }
  }
}
