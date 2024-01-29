package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.AssistantBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AssistantFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Assistant;
import hangouh.me.medi.link.v1.services.AssistantService;
import hangouh.me.medi.link.v1.utils.ResponseUtil;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@V1ApiController
public class AssistantController {
  private final AssistantService assistantService;

  @Autowired
  public AssistantController(AssistantService assistantService) {
    this.assistantService = assistantService;
  }

  @GetMapping("/assistants")
  public ResponseEntity<ResponsePageDTO<Assistant>> getAllAssistants(
      @Valid AssistantFilterDTO dto) {
    return ResponseUtil.createResponsePage(assistantService.getAll(dto), HttpStatus.OK);
  }

  @GetMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Assistant>> getAssistantsById(@PathVariable UUID id) {
    Assistant response = assistantService.getById(id);
    return ResponseUtil.createResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping("/assistants")
  public ResponseEntity<ResponseDTO<Assistant>> createAssistant(
      @Valid @RequestBody AssistantBodyDTO assistantDTO) {
    return ResponseUtil.createResponseEntity(
        assistantService.create(assistantDTO), HttpStatus.CREATED);
  }

  @PutMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Assistant>> updateAssistant(
      @PathVariable UUID id, @Valid @RequestBody AssistantBodyDTO assistantDTO) {
    return ResponseUtil.createResponseEntity(
        assistantService.update(id, assistantDTO), HttpStatus.OK);
  }

  @DeleteMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteAssistant(@PathVariable UUID id) {
    assistantService.delete(id);
    return ResponseUtil.createResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
