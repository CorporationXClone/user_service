package school.faang.user_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SkillCandidateRepository skillCandidateRepository;
    private final SkillMapper skillMapper = SkillMapper.INSTANCE;
    private final SkillCandidateMapper skillCandidateMapper = SkillCandidateMapper.INSTANCE;
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

    @Test
    public void getUserSkills_checkCorrectWork() {
        List<Skill> skills = createListSkills();

        when(userRepository.existsById(1L)).thenReturn(true);
        when(skillRepository.findAllByUserId(1L)).thenReturn(skills);

        List<SkillDto> skillsDto = skillService.getUserSkills(1L);

        assertNotNull(skillsDto);
        assertEquals(1, skillsDto.get(0).getId());
        assertEquals("title1", skillsDto.get(0).getTitle());

        verify(userRepository).existsById(1L);
        verify(skillRepository).findAllByUserId(1L);
    }

    private List<Skill> createListSkills() {
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

    @Test
    public void getUserSkills_ifUserNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> skillService.getUserSkills(1L));
    }

    @Test
    public void getOfferedSkills_checkCorrectWork(){
        when(userRepository.existsById(1L)).thenReturn(true);
        when(skillCandidateRepository.findAllByUserId(1L)).thenReturn(createListCandidateSkills());

        List<SkillCandidateDto> skillsCandidateDto = skillService.getOfferedSkills(1L);

        assertNotNull(skillsCandidateDto);

        assertEquals(3, skillsCandidateDto.size());
        assertEquals(1L, skillsCandidateDto.get(0).getSkillDto().getId());

        verify(skillCandidateRepository).findAllByUserId(1L);
    }

    @Test
    public void getOfferedSkills_ifUserNotExists(){
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataValidException.class, () -> skillService.getOfferedSkills(1L));
    }

    private List<SkillCandidate> createListCandidateSkills(){
        List<Skill> skills = createListSkills();

        SkillCandidate skillCandidate1 = SkillCandidate.builder()
                .skill(skills.get(0))
                .offersAmount(3)
                .build();
        SkillCandidate skillCandidate2 = SkillCandidate.builder()
                .skill(skills.get(1))
                .offersAmount(4)
                .build();
        SkillCandidate skillCandidate3 = SkillCandidate.builder()
                .skill(skills.get(2))
                .offersAmount(5)
                .build();
        return List.of(skillCandidate1, skillCandidate2, skillCandidate3);
    }

    @Test
    public void getOffersAmount_checkCorrectWork(){
        when(userRepository.existsById(1L)).thenReturn(true);
        when(skillRepository.existsById(1L)).thenReturn(true);
        when(skillCandidateRepository.getOffersAmountBySkillIdAndUserId(1L,1L)).thenReturn(10L);

        long result = skillService.getOffersAmount(1L, 1L);

        assertEquals(10, result);
    }

    @Test
    public void getOffersAmount_ifUserNotExists(){
        when(userRepository.existsById(1L)).thenReturn(false);

        DataValidException exception = assertThrows(DataValidException.class, () -> skillService.getOffersAmount(1L, 1L));
        assertEquals("The user does not exist", exception.getMessage());
    }

    @Test
    public void getOffersAmount_ifSkillNotExists(){
        when(skillRepository.existsById(1L)).thenReturn(false);
        when(userRepository.existsById(1L)).thenReturn(true);

        DataValidException exception = assertThrows(DataValidException.class, () -> skillService.getOffersAmount(1L, 1L));
        assertEquals("The skill does not exists", exception.getMessage());
    }
}