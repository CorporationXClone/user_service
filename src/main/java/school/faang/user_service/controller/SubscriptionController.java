package school.faang.user_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.service.SubscriptionService;

@RestController
@AllArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/count/followers/{followeeId}")
    public int getCountSubscribers(@PathVariable long followeeId) {
        return subscriptionService.getCountFollowers(followeeId);
    }
}
