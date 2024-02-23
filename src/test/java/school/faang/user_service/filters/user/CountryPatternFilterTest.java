package school.faang.user_service.filters.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountryPatternFilterTest {
    @InjectMocks
    private CountryPatternFilter countryPatternFilter;
    private static List<User> users;
    private static Country needCountry;

    @BeforeAll
    public static void init() {
        Country country1 = Country.builder()
                .title("Russia")
                .build();
        Country country2 = Country.builder()
                .title("Germany")
                .build();
        needCountry = Country.builder()
                .title("Holland")
                .build();

        users = List.of(User.builder()
                        .id(1L)
                        .country(needCountry)
                        .build(),
                User.builder()
                        .id(2L)
                        .country(country1)
                        .build(),
                User.builder()
                        .id(3L)
                        .country(country2)
                        .build(),
                User.builder()
                        .id(4L)
                        .country(needCountry)
                        .build(),
                User.builder()
                        .id(5L)
                        .country(needCountry)
                        .build());
    }

    @Test
    public void isApplicable_withoutFilters() {
        UserFilterDto userFilterDto = new UserFilterDto();

        assertFalse(countryPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void isApplicable_withBlank() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .countryPattern("   ")
                .build();

        assertFalse(countryPatternFilter.isApplicable(userFilterDto));
    }

    @Test
    public void apply_checkCorrectWork() {
        UserFilterDto userFilterDto = UserFilterDto.builder()
                .countryPattern("Holland")
                .build();

        List<User> result = countryPatternFilter.apply(users.stream(), userFilterDto).toList();

        assertEquals(3, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(4L, result.get(1).getId());
        assertEquals(5L, result.get(2).getId());
    }
}