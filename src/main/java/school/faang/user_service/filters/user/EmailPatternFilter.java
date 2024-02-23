package school.faang.user_service.filters.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class EmailPatternFilter implements UserFilter{
    @Override
    public boolean isApplicable(UserFilterDto filters) {
        return filters.getEmailPattern() != null && !filters.getEmailPattern().isBlank();
    }

    @Override
    public Stream<User> apply(Stream<User> userStream, UserFilterDto filters) {
        return userStream.filter(user -> user.getEmail().toLowerCase().contains(filters.getEmailPattern().toLowerCase()));
    }
}