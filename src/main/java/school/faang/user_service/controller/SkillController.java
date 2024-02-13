package school.faang.user_service.controller;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.SkillService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto create(@Valid @RequestParam SkillDto skillDto) {
        return skillService.create(skillDto);
    }

    @GetMapping("/user/{userId}")
    public List<SkillDto> getUserSkills(@PathVariable long userId) {
        return skillService.getUserSkills(userId);
    }
}
