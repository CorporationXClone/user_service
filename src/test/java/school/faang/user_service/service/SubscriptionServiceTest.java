package school.faang.user_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    public void getCountFollowers_checkCorrectWork() {
        when(userRepository.existsById(1L)).thenReturn(true);

        subscriptionService.getCountFollowers(1L);

        verify(subscriptionRepository).findFollowersAmountByFolloweeId(1L);
    }

    @Test
    public void getCountFollowers_ifUserNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> subscriptionService.getCountFollowers(1L));
    }
}