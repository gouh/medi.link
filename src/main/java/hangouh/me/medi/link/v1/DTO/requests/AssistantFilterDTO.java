package hangouh.me.medi.link.v1.DTO.requests;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantFilterDTO extends PageableDTO {
  private UUID assistantId = null;

  @Size(max = 100)
  private String name = null;

  @Size(max = 150)
  private String contactInfo = null;
}
