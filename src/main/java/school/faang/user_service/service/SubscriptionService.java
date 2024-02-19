package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.exception.SubscriptionException;
import school.faang.user_service.repository.SubscriptionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void unfollowUser(long followerId, long followeeId) {
        if (followerId == followeeId) {
            throw new DataValidException("The user cannot unsubscribe from himself");
        }
        if (!subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new SubscriptionException("The user is not subscribed anyway");
        }
        subscriptionRepository.unfollowUser(followerId, followeeId);
        log.info("The user with id {} has unsubscribed from the user with id {}", followerId, followeeId);
    }
}
