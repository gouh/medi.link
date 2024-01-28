package hangouh.me.medi.link.v1.controllers;

import hangouh.me.medi.link.v1.DTO.requests.AssistantBodyDTO;
import hangouh.me.medi.link.v1.DTO.requests.AssistantFilterDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponseDTO;
import hangouh.me.medi.link.v1.DTO.responses.ResponsePageDTO;
import hangouh.me.medi.link.v1.models.Assistant;
import hangouh.me.medi.link.v1.repositories.AssistantRepository;
import hangouh.me.medi.link.v1.services.UserService;
import hangouh.me.medi.link.v1.utils.RequestUtil;
import jakarta.validation.Valid;
import java.util.UUID;
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
public class AssistantController {
  public static final String ASSISTANT_NOT_FOUND = "Assistant not found";
  private final AssistantRepository assistantRepository;
  private final UserService userService;

  @Autowired
  public AssistantController(AssistantRepository assistantRepository, UserService userService) {
    this.assistantRepository = assistantRepository;
    this.userService = userService;
  }

  @GetMapping("/assistants")
  public ResponseEntity<ResponsePageDTO<Assistant>> getAllAssistants(
      @Valid AssistantFilterDTO dto) {
    Sort sort = RequestUtil.buildSort(dto.getSortBy());
    Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), sort);
    Page<Assistant> assistants = assistantRepository.findByFilters(dto, pageable);
    return ResponseEntity.ok(new ResponsePageDTO<>(HttpStatus.OK, "", assistants));
  }

  @GetMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Assistant>> getAssistantsById(@PathVariable UUID id) {
    return assistantRepository
        .findById(id)
        .map(
            assistant ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.OK, "", assistant), HttpStatus.OK))
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, ASSISTANT_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }

  @PostMapping("/assistants")
  public ResponseEntity<ResponseDTO<Assistant>> createAssistant(
      @Valid @RequestBody AssistantBodyDTO assistantDTO) {
    Assistant assistant = assistantDTO.toAssistant();
    if (assistantDTO.getUser() != null) {
      assistant.setUser(this.userService.upserUser(assistantDTO.getUser(), null));
    }
    Assistant savedAssistant = assistantRepository.save(assistant);
    return new ResponseEntity<>(
        new ResponseDTO<>(HttpStatus.CREATED, "", savedAssistant), HttpStatus.CREATED);
  }

  @PutMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Assistant>> updateAssistant(
      @PathVariable UUID id, @Valid @RequestBody AssistantBodyDTO assistantDTO) {
    return assistantRepository
        .findById(id)
        .map(
            assistant -> {
              assistant.setName(assistantDTO.getName());
              assistant.setContactInfo(assistantDTO.getContactInfo());
              if (assistant.getUser() != null && assistantDTO.getUser() != null) {
                assistant.setUser(
                    this.userService.upserUser(
                        assistantDTO.getUser(), assistant.getUser().getUserId()));
              }
              Assistant updatedAssistant = assistantRepository.save(assistant);
              return new ResponseEntity<>(
                  new ResponseDTO<>(HttpStatus.OK, "", updatedAssistant), HttpStatus.OK);
            })
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, ASSISTANT_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/assistants/{id}")
  public ResponseEntity<ResponseDTO<Void>> deleteAssistant(@PathVariable UUID id) {
    return assistantRepository
        .findById(id)
        .map(
            assistant -> {
              assistantRepository.delete(assistant);
              return new ResponseEntity<>(
                  new ResponseDTO<Void>(HttpStatus.NO_CONTENT, "", null), HttpStatus.NO_CONTENT);
            })
        .orElseGet(
            () ->
                new ResponseEntity<>(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND, ASSISTANT_NOT_FOUND, null),
                    HttpStatus.NOT_FOUND));
  }
}
