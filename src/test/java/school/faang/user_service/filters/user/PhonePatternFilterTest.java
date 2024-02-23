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
class PhonePatternFilterTest {
    @InjectMocks
    private PhonePatternFilter phonePatternFilter;
    private static List<User> users;

    @BeforeAll
    public static void init() {
        users = List.of(User.builder()
                        .id(1L)
                        .phone("phone-1")
                        .build(),
                User.builder()
                        .id(2L)
                        .phone("phone-NEED")
                        .build(),
                User.builder()
                        .id(3L)
                        .phone("phone-3")
                        .build(),
                User.builder()
                        .id(4L)
                        .phone("phone-NEED")
                        .build(),
                User.builder()
                        .id(5L)
                        .phone("phone-5")
                        .build());
    }

    @Test
    public void isApplicable_withoutFilter() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(phonePatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .phonePattern("   ")
                .build();

        assertFalse(phonePatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .phonePattern("phone-NEED")
                .build();

        List<User> result = phonePatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(2, result.size());

        assertEquals(2L, result.get(0).getId());
        assertEquals(4L, result.get(1).getId());
    }
}