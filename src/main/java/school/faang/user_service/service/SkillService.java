package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.SkillCandidate;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.mapper.SkillCandidateMapper;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillCandidateRepository;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final SkillCandidateRepository skillCandidateRepository;
    private final SkillMapper skillMapper = SkillMapper.INSTANCE;
    private final SkillCandidateMapper skillCandidateMapper = SkillCandidateMapper.INSTANCE;

    public SkillDto create(SkillDto skillDto) {
        if (skillRepository.existsByTitle(skillDto.getTitle())) {
            throw new DataValidException("Skill already exists");
        }

        Skill skill = skillRepository.save(skillMapper.toEntity(skillDto));
        return skillMapper.toDto(skill);
    }
    public List<SkillDto> getUserSkills(long userId) {
        validationUser(userId);

        List<Skill> skills = skillRepository.findAllByUserId(userId);
        return skillMapper.mapListToDto(skills);
    }

    public List<SkillCandidateDto> getOfferedSkills(long userId){
        validationUser(userId);

        List<SkillCandidate> skillCandidates = skillCandidateRepository.findAllByUserId(userId);
        return skillCandidates.stream().map(skillCandidateMapper::toDto).toList();
    }

    public long getOffersAmount(long userId, long skillId){
        validationUser(userId);
        validationSkill(skillId);

        return skillCandidateRepository.getOffersAmountBySkillIdAndUserId(userId, skillId);
    }
    private void validationUser(long userId){
        if (!userRepository.existsById(userId)) {
            throw new DataValidException("The user does not exist");
        }
    }
    private void validationSkill(long skillId){
        if(!skillRepository.existsById(skillId)){
            throw new DataValidException("The skill does not exists");
        }
    }
}
