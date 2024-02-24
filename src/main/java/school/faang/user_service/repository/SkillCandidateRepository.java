package school.faang.user_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.SkillCandidate;

import java.util.List;

@Repository
public interface SkillCandidateRepository extends JpaRepository<SkillCandidate, Long> {

    @Query(nativeQuery = true, value = """
            SELECT us.* FROM user_skill us
            JOIN skill s ON us.skill_id = s.id
            WHERE us.user_id = :userId
            """)
    List<SkillCandidate> findAllByUserId(long userId);

    @Query(nativeQuery = true, value = """
            SELECT us.offers_amount FROM user_skill us
            JOIN skill s ON us.skill_id = s.id
            WHERE us.user_id = :userId AND us.skill_id = :skillId
            """)
    long getOffersAmountBySkillIdAndUserId(long userId, long skillId);
}
