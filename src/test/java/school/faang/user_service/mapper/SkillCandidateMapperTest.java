package school.faang.user_service.mapper;

import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.SkillCandidate;

import static org.junit.jupiter.api.Assertions.*;

class SkillCandidateMapperTest {
    private final SkillCandidateMapper skillCandidateMapper = SkillCandidateMapper.INSTANCE;

    @Test
    public void toDto_checkCorrectWork(){
        Skill skillEntity = Skill.builder()
                .id(1L)
                .title("title")
                .build();

        SkillCandidate skillCandidate = SkillCandidate.builder()
                .skill(skillEntity)
                .offersAmount(3L)
                .build();

        SkillCandidateDto skillCandidateDto = skillCandidateMapper.toDto(skillCandidate);

        assertNotNull(skillCandidateDto);

        assertEquals(1L, skillCandidateDto.getSkillDto().getId());
        assertEquals(3L, skillCandidateDto.getOffersAmount());
    }

    @Test
    public void toEntity_checkCorrectWork(){
        SkillDto skillDto = SkillDto.builder()
                .id(1L)
                .title("title")
                .build();

        SkillCandidateDto skillCandidateDto = SkillCandidateDto.builder()
                .skillDto(skillDto)
                .offersAmount(3L)
                .build();

        SkillCandidate skillCandidate = skillCandidateMapper.toEntity(skillCandidateDto);

        assertNotNull(skillCandidate);

        assertEquals(1L, skillCandidate.getSkill().getId());
        assertEquals(3L, skillCandidate.getOffersAmount());
    }
}