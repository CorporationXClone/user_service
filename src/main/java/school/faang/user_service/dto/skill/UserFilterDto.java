package school.faang.user_service.dto.skill;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterDto {
    private String namePattern;
    private String aboutPattern;
    private String emailPattern;
    private String contactPattern;
    private String countryPattern;
    private String cityPattern;
    private String phonePattern;
    private String skillPattern;
    @Min(0)
    private int experienceMin;
    @Min(0)
    private int experienceMax;
    private int page;
    private int pageSize;
}
