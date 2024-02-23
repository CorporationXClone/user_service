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
class EmailPatternFilterTest {
    @InjectMocks
    private EmailPatternFilter emailPatternFilter;
    private static List<User> users;

    @BeforeAll
    public static void init(){
        users = List.of(
                User.builder()
                        .id(1L)
                        .email("email-1")
                        .build(),
                User.builder()
                        .id(2L)
                        .email("email-Need")
                        .build(),
                User.builder()
                        .id(3L)
                        .email("email-Need")
                        .build(),
                User.builder()
                        .id(4L)
                        .email("email-2")
                        .build(),
                User.builder()
                        .id(5L)
                        .email("email-Need")
                        .build()
        );
    }

    @Test
    public void isApplicable_withoutFilter(){
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(emailPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank(){
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .emailPattern("email-Need")
                .build();

        List<User> result = emailPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(3, result.size());

        assertEquals(2L, result.get(0).getId());
        assertEquals(3L, result.get(1).getId());
        assertEquals(5L, result.get(2).getId());
    }
}