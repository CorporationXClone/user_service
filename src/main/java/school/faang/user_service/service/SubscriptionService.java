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
    public void followUser(long followerId, long followeeId){
        if (followerId == followeeId) {
            throw new DataValidException("The user cannot subscribe to himself");
        }
        if(subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)){
            throw new SubscriptionException("The subscription already exists");
        }
        subscriptionRepository.followUser(followerId, followeeId);
        log.info("User with id {} subscription on user with id {}", followerId, followeeId);
    }
}
