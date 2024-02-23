package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final List<UserFilter> userFilters;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Transactional
    public List<UserDto> getFollowers(long followeeId, UserFilterDto filterDto) {
        validOnExistUser(followeeId);
        return filters(subscriptionRepository.findByFolloweeId(followeeId), filterDto);
    }

    private List<UserDto> filters(Stream<User> users, UserFilterDto filterDto) {
        if (userFilters.stream().noneMatch(filter -> filter.isApplicable(filterDto))) {
            return users.map(userMapper::toDto).toList();
        }

        return userFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .flatMap(filter -> filter.apply(users, filterDto))
                .skip(filterDto.getPage() > 0 ? (long) (filterDto.getPage() - 1) * filterDto.getPageSize() : 0)
                .limit(filterDto.getPage() > 0 && filterDto.getPageSize() > 0 ? filterDto.getPageSize() : Long.MAX_VALUE)
                .map(userMapper::toDto)
                .toList();
    }

    private void validOnExistUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataValidException(String.format("The user with ID %d not exists", userId));
        }
    }
}
