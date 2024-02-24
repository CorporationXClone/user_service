package school.faang.user_service.controller;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.service.SkillService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillController {
    private final SkillService skillService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto create(@Valid @RequestBody SkillDto skillDto) {
        return skillService.create(skillDto);
    }

    @GetMapping("/user/{userId}")
    public List<SkillDto> getUserSkills(@PathVariable long userId) {
        return skillService.getUserSkills(userId);
    }

    @GetMapping("/candidates/{userId}")
    public List<SkillCandidateDto> getOfferedSkills(@PathVariable long userId){
        return skillService.getOfferedSkills(userId);
    }

    @GetMapping("/offers/{userId}/{skillId}")
    public long getOffersAmount(@PathVariable long userId, @PathVariable long skillId){
        return skillService.getOffersAmount(userId, skillId);
    }
}
