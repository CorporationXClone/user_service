package school.faang.user_service.mapper;

import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;

import java.util.List;

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

    @Test
    public void mapListToDto_checkCorrectWork() {
        List<Skill> skills = createListSkillsEntity();

        List<SkillDto> skillsDto = skillMapper.mapListToDto(skills);

        assertNotNull(skillsDto);
        assertEquals(3, skillsDto.size());

        assertEquals(1L, skillsDto.get(0).getId());
        assertEquals("title1", skillsDto.get(0).getTitle());
    }

    private List<Skill> createListSkillsEntity() {
        Skill skill1 = Skill.builder()
                .id(1L)
                .title("title1")
                .build();
        Skill skill2 = Skill.builder()
                .id(2L)
                .title("title2")
                .build();
        Skill skill3 = Skill.builder()
                .id(3L)
                .title("title3")
                .build();
        return List.of(skill1, skill2, skill3);
    }
}