package hangouh.me.medi.link.v1.services;

import hangouh.me.medi.link.v1.DTO.requests.AppointmentBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AppointmentFilterDTO;
import hangouh.me.medi.link.v1.models.Appointment;
import hangouh.me.medi.link.v1.models.Assistant;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.models.Patient;
import hangouh.me.medi.link.v1.repositories.AppointmentRepository;
import hangouh.me.medi.link.v1.repositories.AssistantRepository;
import hangouh.me.medi.link.v1.repositories.DoctorRepository;
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
public class AppointmentServiceImpl implements AppointmentService {
  private static final String NOT_FOUND = "Appointment not found with id: %s";
  private static final String ASSOCIATION_NOT_FOUND = "Doctor, patient or assistant not found";
  private final AppointmentRepository appointmentRepository;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final AssistantRepository assistantRepository;

  @Autowired
  public AppointmentServiceImpl(
      AppointmentRepository appointmentRepository,
      PatientRepository patientRepository,
      DoctorRepository doctorRepository,
      AssistantRepository assistantRepository) {
    this.appointmentRepository = appointmentRepository;
    this.patientRepository = patientRepository;
    this.doctorRepository = doctorRepository;
    this.assistantRepository = assistantRepository;
  }

  public Page<Appointment> getAll(AppointmentFilterDTO filters) {
    Sort sort = RequestUtil.buildSort(filters.getSortBy());
    Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), sort);
    return appointmentRepository.findByFilters(filters, pageable);
  }

  public Appointment getById(UUID id) {
    return appointmentRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public Appointment create(AppointmentBodyDTO appointment) {
    Optional<Patient> patient = patientRepository.findById(appointment.getPatientId());
    Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctorId());
    Optional<Assistant> assistant = assistantRepository.findById(appointment.getAssistantId());
    if (patient.isPresent() && doctor.isPresent() && assistant.isPresent()) {
      Appointment newAppointment =
          appointment.toAppointment(patient.get(), doctor.get(), assistant.get());
      return appointmentRepository.save(newAppointment);
    } else {
      throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
    }
  }

  public Appointment update(UUID id, AppointmentBodyDTO appointment) {
    return appointmentRepository
        .findById(id)
        .map(
            updatedAppointment -> {
              Optional<Patient> patient = patientRepository.findById(appointment.getPatientId());
              Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctorId());
              Optional<Assistant> assistant =
                  assistantRepository.findById(appointment.getAssistantId());
              if (patient.isPresent() && doctor.isPresent() && assistant.isPresent()) {
                updatedAppointment.setPatient(patient.get());
                updatedAppointment.setDoctor(doctor.get());
                updatedAppointment.setAssistant(assistant.get());
                updatedAppointment.setDateTime(appointment.getDateTime());
                updatedAppointment.setConsultationReason(appointment.getConsultationReason());
                return appointmentRepository.save(updatedAppointment);
              }
              throw new EntityNotFoundException(ASSOCIATION_NOT_FOUND);
            })
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND.formatted(id)));
  }

  public void delete(UUID id) {
    if (appointmentRepository.existsById(id)) {
      appointmentRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(NOT_FOUND.formatted(id));
    }
  }
}
