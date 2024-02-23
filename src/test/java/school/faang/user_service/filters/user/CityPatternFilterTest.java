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
class CityPatternFilterTest {
    @InjectMocks
    private CityPatternFilter cityPatternFilter;
    private static List<User> users;

    @BeforeAll
    static void init() {
        users = List.of(User.builder()
                        .id(1L)
                        .city("Moscow")
                        .build(),
                User.builder()
                        .id(2L)
                        .city("Paris")
                        .build(),
                User.builder()
                        .id(3L)
                        .city("Moscow")
                        .build(),
                User.builder()
                        .id(4L)
                        .city("Novosib")
                        .build(),
                User.builder()
                        .id(5L)
                        .city("Moscow")
                        .build());
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(cityPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank() {
        UserFilterDto userFilterDto = UserFilterDto.builder().cityPattern("   ").build();

        assertFalse(cityPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder().cityPattern("Moscow").build();

        List<User> result = cityPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(3, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(3L, result.get(1).getId());
        assertEquals(5L, result.get(2).getId());
    }
}