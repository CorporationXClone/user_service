package school.faang.user_service.filters.user;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.skill.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

@Component
public class ContactPatternFilter implements UserFilter{
    @Override
    public boolean isApplicable(UserFilterDto filters) {
        return filters.getContactPattern() != null && !filters.getContactPattern().isBlank();
    }

    @Override
    public Stream<User> apply(Stream<User> userStream, UserFilterDto filters) {
        return userStream.filter(user -> user.getContacts().stream()
                                                                .anyMatch(contact -> contact.getContact()
                                                                        .toLowerCase()
                                                                        .contains(filters.getContactPattern().toLowerCase())));
    }
}
