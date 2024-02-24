package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import school.faang.user_service.dto.skill.SkillCandidateDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.SkillCandidate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillCandidateMapper {
    SkillCandidateMapper INSTANCE = Mappers.getMapper(SkillCandidateMapper.class);

    @Mapping(source = "skill", target = "skillDto", qualifiedByName = "skillToDto")
    SkillCandidateDto toDto(SkillCandidate skillCandidate);

    @Mapping(source = "skillDto", target = "skill", qualifiedByName = "skillToEntity")
    SkillCandidate toEntity(SkillCandidateDto skillCandidateDto);

    @Named("skillToDto")
    SkillDto skillToDto(Skill skill);

    @Named("skillToEntity")
    Skill skillToEntity(SkillDto skillDto);
}
