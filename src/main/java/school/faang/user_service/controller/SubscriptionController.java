package school.faang.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.service.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/followers/{followeeId}")
    public List<UserDto> getFollowers(@PathVariable long followeeId,@Valid @RequestBody UserFilterDto filtersDto){
        return subscriptionService.getFollowers(followeeId, filtersDto);
    }
}
