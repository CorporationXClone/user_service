package school.faang.user_service.mapper;

import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;

import static org.junit.jupiter.api.Assertions.*;

class SkillMapperTest {
    private final SkillMapper skillMapper = SkillMapper.INSTANCE;

    @Test
    public void toDto_checkCorrectWork() {
        Skill skillEntity = Skill.builder()
                .id(1)
                .title("title")
                .build();

        SkillDto skillDto = skillMapper.toDto(skillEntity);

        assertNotNull(skillDto);
        assertEquals(1, skillDto.getId());
        assertEquals("title", skillDto.getTitle());
    }

    @Test
    public void toEntity_checkCorrectWork() {
        SkillDto skillDto = SkillDto.builder()
                .id(1L)
                .title("title")
                .build();

        Skill skillEntity = skillMapper.toEntity(skillDto);

        assertNotNull(skillEntity);
        assertEquals(1, skillEntity.getId());
        assertEquals("title", skillEntity.getTitle());
    }
}