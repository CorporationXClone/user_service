package school.faang.user_service.filters.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class NamePatternFilter implements UserFilter{
    @Override
    public boolean isApplicable(UserFilterDto filters) {
        return filters.getNamePattern() != null && !filters.getNamePattern().isBlank();
    }
    @Override
    public Stream<User> apply(Stream<User> userStream, UserFilterDto filters) {
        return userStream.filter(user -> user.getUsername().toLowerCase().contains(filters.getNamePattern().toLowerCase()));
    }
}
