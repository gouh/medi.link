package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.DoctorBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.repository.DoctorRepository;
import hangouh.me.medi.link.v1.utls.RequestUtils;
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
public class DoctorController {
  public static final String DOCTOR_NOT_FOUND = "Doctor not found";
  private final DoctorRepository doctorRepository;

  @Autowired
  public DoctorController(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  @GetMapping("/doctors")
  public ResponseEntity<ResponsePageDTO<Doctor>> getAllDoctors(@Valid DoctorFilterDTO dto) {
    Sort sort = RequestUtils.buildSort(dto.getSortBy());
    Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), sort);
    Page<Doctor> doctors = doctorRepository.findByFilters(dto, pageable);
    return ResponseEntity.ok(new ResponsePageDTO<>(HttpStatus.OK, "", doctors));
  }

  @GetMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Doctor>> getDoctorById(@PathVariable UUID id) {
    return doctorRepository
        .findById(id)
        .map(
            doctor ->
                new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "", doctor), HttpStatus.OK))
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, DOCTOR_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }

  @PostMapping("/doctors")
  public ResponseEntity<ResponseDTO<Doctor>> createDoctor(
      @Valid @RequestBody DoctorBodyDTO doctorDTO) {
    Doctor doctor = doctorDTO.toDoctor();
    Doctor savedDoctor = doctorRepository.save(doctor);
    return new ResponseEntity<>(
        new ResponseDTO<>(HttpStatus.CREATED, "", savedDoctor), HttpStatus.CREATED);
  }

  @PutMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Doctor>> updateDoctor(
      @PathVariable UUID id, @Valid @RequestBody DoctorBodyDTO doctorDTO) {
    return doctorRepository
        .findById(id)
        .map(
            doctor -> {
              doctor.setName(doctorDTO.getName());
              doctor.setSpecialty(doctorDTO.getSpecialty());
              doctor.setContactInfo(doctorDTO.getContactInfo());
              Doctor updatedDoctor = doctorRepository.save(doctor);
              return new ResponseEntity<>(
                  new ResponseDTO<>(HttpStatus.OK, "", updatedDoctor), HttpStatus.OK);
            })
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, DOCTOR_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteDoctor(@PathVariable UUID id) {
    return doctorRepository
        .findById(id)
        .map(
            doctor -> {
              doctorRepository.delete(doctor);
              return new ResponseEntity<>(
                  new ResponseDTO<Void>(HttpStatus.NO_CONTENT, "", null), HttpStatus.NO_CONTENT);
            })
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, DOCTOR_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }
}
