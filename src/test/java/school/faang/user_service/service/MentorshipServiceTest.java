package school.faang.user_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.mentorship.MentorshipRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorshipServiceTest {
    @Mock
    private MentorshipRepository mentorshipRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MentorshipService mentorshipService;

    @Test
    public void getMentees_checkCorrectWork() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(mentorshipRepository.getMenteesByMentorId(1L)).thenReturn(createMentees());

        List<UserDto> result = mentorshipService.getMentees(1L);

        assertNotNull(result);

        assertEquals(3, result.size());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    public void getMentees_ifMentorNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> mentorshipService.getMentees(1L));
    }

    @Test
    public void deleteMentor_checkCorrectWork() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);

        when(mentorshipRepository.existsByMentorIdAndMenteeId(1L, 2L)).thenReturn(true);

        mentorshipService.deleteMentor(1L, 2L);

        verify(mentorshipRepository).deleteMentorFromMentee(1L, 2L);
    }

    @Test
    public void deleteMentor_ifMentorNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> mentorshipService.deleteMentor(1L, 2L));
    }

    @Test
    public void deleteMentor_ifMenteeNotExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> mentorshipService.deleteMentor(1L, 2L));
    }

    @Test
    public void deleteMentor_ifRelationshipNotExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);

        when(mentorshipRepository.existsByMentorIdAndMenteeId(1L, 2L)).thenReturn(false);

        DataValidException exception = assertThrows(DataValidException.class,
                () -> mentorshipService.deleteMentor(1L, 2L));

        assertEquals("There is no such mentoring relationship", exception.getMessage());
    }

    @Test
    public void deleteMentee_checkCorrectWork() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);

        when(mentorshipRepository.existsByMentorIdAndMenteeId(1L, 2L)).thenReturn(true);

        mentorshipService.deleteMentee(2L, 1L);

        verify(mentorshipRepository).deleteMenteeFromMentor(2L, 1L);
    }

    @Test
    public void deleteMentee_ifMenteeNotExists() {
        when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> mentorshipService.deleteMentee(2L, 1L));
    }

    @Test
    public void deleteMentee_ifMentorNotExists() {
        when(userRepository.existsById(2L)).thenReturn(true);
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> mentorshipService.deleteMentee(2L, 1L));
    }

    @Test
    public void deleteMentee_ifRelationshipNotExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);

        when(mentorshipRepository.existsByMentorIdAndMenteeId(1L, 2L)).thenReturn(false);

        DataValidException exception = assertThrows(DataValidException.class,
                () -> mentorshipService.deleteMentee(2L, 1L));

        assertEquals("There is no such mentoring relationship", exception.getMessage());
    }

    private List<User> createMentees() {
        User mentee1 = User.builder()
                .id(1L)
                .username("mentee1")
                .email("email-1")
                .build();
        User mentee2 = User.builder()
                .id(2L)
                .username("mentee2")
                .email("email-2")
                .build();
        User mentee3 = User.builder()
                .id(3L)
                .username("mentee3")
                .email("email-3")
                .build();
        return List.of(mentee1, mentee2, mentee3);
    }
}