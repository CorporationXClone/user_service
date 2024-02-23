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
class ExperienceMinFilterTest {
    @InjectMocks
    private ExperienceMinFilter experienceMinFilter;
    private static List<User> users;

    @BeforeAll
    public static void init() {
        users = List.of(
                User.builder()
                        .id(1L)
                        .experience(2)
                        .build(),
                User.builder()
                        .id(2L)
                        .experience(11)
                        .build(),
                User.builder()
                        .id(3L)
                        .experience(8)
                        .build(),
                User.builder()
                        .id(4L)
                        .experience(1)
                        .build(),
                User.builder()
                        .id(5L)
                        .experience(4)
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(experienceMinFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .experienceMin(7)
                .build();

        List<User> result = experienceMinFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(2, result.size());

        assertEquals(2L, result.get(0).getId());
        assertEquals(3L, result.get(1).getId());
    }
}