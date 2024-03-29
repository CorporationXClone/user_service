package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.mentorship.MentorshipRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipService {
    private final MentorshipRepository mentorshipRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public List<UserDto> getMentees(long mentorId) {
        validationUser(mentorId);

        List<User> mentees = mentorshipRepository.getMenteesByMentorId(mentorId);
        return mentees.stream().map(userMapper::toDto).toList();
    }

    private void validationUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataValidException(String.format("User with id %d not exist", userId));
        }
    }
}
