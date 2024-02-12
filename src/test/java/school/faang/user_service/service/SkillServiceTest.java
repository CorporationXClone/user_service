package school.faang.user_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    private final SkillMapper skillMapper = SkillMapper.INSTANCE;
    private Skill skill;
    private SkillDto skillDto;

    @InjectMocks
    private SkillService skillService;


    @BeforeEach
    void setUp() {
        skill = Skill.builder()
                .id(1)
                .title("title")
                .build();
        skillDto = skillMapper.toDto(skill);
    }

    @Test
    public void create_checkCorrectWork() {
        when(skillRepository.existsByTitle(skill.getTitle())).thenReturn(false);
        when(skillRepository.save(skill)).thenReturn(skill);

        SkillDto result = skillService.create(skillDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("title", result.getTitle());

        verify(skillRepository).existsByTitle(skillDto.getTitle());
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    public void create_ifAlreadyExists() {
        when(skillRepository.existsByTitle(skill.getTitle())).thenReturn(true);

        assertThrows(DataValidException.class, () -> skillService.create(skillDto));
    }
}