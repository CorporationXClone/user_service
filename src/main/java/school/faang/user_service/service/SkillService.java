package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final SkillMapper skillMapper = SkillMapper.INSTANCE;

    public SkillDto create(SkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.getTitle())) {
            throw new DataValidException("Skill already exists");
        }

        Skill skill = skillRepository.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(skill);
    }
    public List<SkillDto> getUserSkills(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataValidException("The user does not exist");
        }

        List<Skill> skills = skillRepository.findAllByUserId(userId);
        return skillMapper.mapListToDto(skills);
    }
}
