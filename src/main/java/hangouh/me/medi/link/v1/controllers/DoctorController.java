package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.DoctorBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.DoctorFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Doctor;
import hangouh.me.medi.link.v1.services.DoctorService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class DoctorController {
  private final DoctorService doctorService;

  @Autowired
  public DoctorController(DoctorService doctorService) {
    this.doctorService = doctorService;
  }

  @GetMapping("/doctors")
  public ResponseEntity<ResponsePageDTO<Doctor>> getAllDoctors(@Valid DoctorFilterDTO dto) {
    return ResponseUtil.createResponsePage(doctorService.getAll(dto), HttpStatus.OK);
  }

  @GetMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Doctor>> getDoctorById(@PathVariable UUID id) {
    Doctor response = doctorService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/doctors")
  public ResponseEntity<ResponseDTO<Doctor>> createDoctor(
      @Valid @RequestBody DoctorBodyDTO doctorDTO) {
    return ResponseUtil.createResponseEntity(doctorService.create(doctorDTO), HttpStatus.CREATED);
  }

  @PutMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Doctor>> updateDoctor(
      @PathVariable UUID id, @Valid @RequestBody DoctorBodyDTO doctorDTO) {
    return ResponseUtil.createResponseEntity(doctorService.update(id, doctorDTO), HttpStatus.OK);
  }

  @DeleteMapping("/doctors/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteDoctor(@PathVariable UUID id) {
    doctorService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
