package hangouh.me.medi.link.v1.DTO.requests;

import hangouh.me.medi.link.v1.models.Assistant;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssistantBodyDTO {
  @NotNull @NotEmpty
  @Size(max = 255)
  private String name;

  @NotNull @NotEmpty
  @Size(max = 255)
  private String contactInfo;

  @Nullable @Valid private UserBodyDTO user;

  public Assistant toAssistant() {
    Assistant assistant = new Assistant();
    assistant.setName(this.name);
    assistant.setContactInfo(this.contactInfo);
    return assistant;
  }
}
