package school.faang.user_service.filters.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AboutPatternFilterTest {
    @InjectMocks
    private AboutPatternFilter aboutPatternFilter;
    private static List<User> users;

    @BeforeAll
    static void init() {
        users = List.of(
                User.builder()
                        .id(1L)
                        .aboutMe("Java Dev")
                        .build(),
                User.builder()
                        .id(2L)
                        .aboutMe("Java Dev")
                        .build(),
                User.builder()
                        .id(3L)
                        .aboutMe("Dev")
                        .build(),
                User.builder()
                        .id(4L)
                        .aboutMe("Python Dev")
                        .build(),
                User.builder()
                        .id(5L)
                        .aboutMe("Intern")
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(aboutPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlankFilter() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .aboutPattern("   ")
                .build();

        assertFalse(aboutPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .aboutPattern("Java Dev")
                .build();
        List<User> result = aboutPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }
}