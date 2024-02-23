package school.faang.user_service.filters.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SkillPatternFilterTest {
    @InjectMocks
    private SkillPatternFilter skillPatternFilter;
    private static List<User> users;

    @BeforeAll
    public static void init(){
        users = List.of(
                User.builder()
                        .id(1L)
                        .skills(List.of(
                                Skill.builder()
                                        .title("C++")
                                        .build(),
                                Skill.builder()
                                        .title("Java")
                                        .build(),
                                Skill.builder()
                                        .title("Go")
                                        .build()
                        ))
                        .build(),
                User.builder()
                        .id(2L)
                        .skills(List.of(
                                Skill.builder()
                                        .title("C")
                                        .build(),
                                Skill.builder()
                                        .title("pascal")
                                        .build(),
                                Skill.builder()
                                        .title("Go")
                                        .build()
                        ))
                        .build(),
                User.builder()
                        .id(3L)
                        .skills(List.of(
                                Skill.builder()
                                        .title("Python")
                                        .build(),
                                Skill.builder()
                                        .title("Java")
                                        .build(),
                                Skill.builder()
                                        .title("Go")
                                        .build()
                        ))
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter(){
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(skillPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank(){
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .skillPattern("   ")
                .build();

        assertFalse(skillPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork(){
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .skillPattern("Java")
                .build();

        List<User> result = skillPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(3L, result.get(1).getId());
    }
}