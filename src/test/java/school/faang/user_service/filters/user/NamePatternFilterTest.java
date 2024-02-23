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
class NamePatternFilterTest {
    @InjectMocks
    private NamePatternFilter namePatternFilter;
    private static List<User> users;

    @BeforeAll
    public static void init() {
        users = List.of(
                User.builder()
                        .id(1L)
                        .username("Vasya")
                        .build(),
                User.builder()
                        .id(2L)
                        .username("username")
                        .build(),
                User.builder()
                        .id(3L)
                        .username("MAAAAAX")
                        .build(),
                User.builder()
                        .id(4L)
                        .username("username")
                        .build(),
                User.builder()
                        .id(5L)
                        .username("Vila")
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(namePatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .namePattern("   ")
                .build();

        assertFalse(namePatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .namePattern("username")
                .build();

        List<User> result = namePatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(2, result.size());

        assertEquals(2L, result.get(0).getId());
        assertEquals(4L, result.get(1).getId());
    }
}