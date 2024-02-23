package school.faang.user_service.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.repository.SubscriptionRepository;
import school.faang.user_service.repository.UserRepository;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public int getCountFollowers(long followeeId) {
        validOnExistUser(followeeId);
        return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
    }

    private void validOnExistUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataValidException(String.format("The user with ID %d not exists", userId));
        }
    }
}
