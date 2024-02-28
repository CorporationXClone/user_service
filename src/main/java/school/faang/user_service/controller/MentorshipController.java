package school.faang.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.skill.UserDto;
import school.faang.user_service.service.MentorshipService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mentorship")
public class MentorshipController {
    private final MentorshipService mentorshipService;

    @GetMapping("/mentees/{mentorId}")
    public List<UserDto> getMentees(@PathVariable long mentorId) {
        return mentorshipService.getMentees(mentorId);
    }

    @DeleteMapping("/mentor/{mentorId}/{menteeId}")
    public void deleteMentor(@PathVariable long mentorId, @PathVariable long menteeId) {
        mentorshipService.deleteMentor(mentorId, menteeId);
    }

    @DeleteMapping("/mentee/{menteeId}/{mentorId}")
    public void deleteMentee(@PathVariable long menteeId, @PathVariable long mentorId) {
        mentorshipService.deleteMentee(menteeId, mentorId);
    }
}
