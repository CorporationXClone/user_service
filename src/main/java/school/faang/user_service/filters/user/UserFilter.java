package school.faang.user_service.filters.user;

import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.filters.Filter;

import java.util.stream.Stream;


public interface UserFilter {
    boolean isApplicable(UserFilterDto filters);
    Stream<User> apply(Stream<User> users, UserFilterDto filters);
}
