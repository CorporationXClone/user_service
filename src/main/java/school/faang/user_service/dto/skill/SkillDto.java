package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SkillDto {
    private Long id;
    @NotNull(message = "Title can't be empty")
    @NotBlank(message = "Title cannot be blank")
    private String title;
}
