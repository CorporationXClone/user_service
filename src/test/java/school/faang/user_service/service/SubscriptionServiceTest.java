package school.faang.user_service.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.filters.user.UserFilter;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
    private static UserRepository userRepository;
    private static SubscriptionRepository subscriptionRepository;
    private static UserFilter userFilter;
    private static SubscriptionService subscriptionService;
    private static UserFilterDto userFilterDto;
    private static List<User> users;

    @BeforeAll
    public static void init() {
        userRepository = Mockito.mock(UserRepository.class);
        subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        userFilter = Mockito.mock(UserFilter.class);

        subscriptionService = new SubscriptionService(userRepository, subscriptionRepository, List.of(userFilter));

        userFilterDto = new UserFilterDto();

        users = List.of(
                User.builder()
                        .id(1L)
                        .build(),
                User.builder()
                        .id(2L)
                        .build(),
                User.builder()
                        .id(3L)
                        .build()
        );
    }

    @Test
    public void getFollowers_ifUserNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(DataValidException.class, () -> subscriptionService.getFollowers(1L, new UserFilterDto()));
    }

    @Test
    public void getFollowers_withoutFilters() {
        when(userFilter.isApplicable(userFilterDto)).thenReturn(false);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.findByFolloweeId(1L)).thenReturn(users.stream());

        List<UserDto> result = subscriptionService.getFollowers(1L, userFilterDto);

        verify(userFilter, times(0)).apply(users.stream(), userFilterDto);
        assertEquals(3, result.size());
    }

    @Test
    public void getFollowers_checkCorrectWork() {
        Stream<User> userStream = users.stream();

        when(userFilter.isApplicable(userFilterDto)).thenReturn(true);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.findByFolloweeId(1L)).thenReturn(userStream);
        when(userFilter.apply(userStream, userFilterDto)).thenReturn(userStream);

        List<UserDto> result = subscriptionService.getFollowers(1L, userFilterDto);

        verify(userFilter).apply(userStream, userFilterDto);
        assertEquals(3, result.size());
    }
}