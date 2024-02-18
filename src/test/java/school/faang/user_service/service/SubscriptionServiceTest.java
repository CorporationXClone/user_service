package school.faang.user_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.exception.SubscriptionException;
import school.faang.user_service.repository.SubscriptionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private  SubscriptionService subscriptionService;

    @Test
    public void followUser_checkCorrectWork(){
        when(subscriptionRepository.existsByFollowerIdAndFolloweeId(1L, 10L)).thenReturn(false);

        subscriptionService.followUser(1L, 10L);

        verify(subscriptionRepository).followUser(1L, 10L);
    }

    @Test
    public void followUser_subscribeToYourself(){
        assertThrows(DataValidException.class, () -> subscriptionService.followUser(1L, 1L));
    }

    @Test
    public void followUser_ifExists(){
        when(subscriptionRepository.existsByFollowerIdAndFolloweeId(1L, 10L)).thenReturn(true);
        assertThrows(SubscriptionException.class, () -> subscriptionService.followUser(1L, 10L));
    }
}