package school.faang.user_service.repository.mentorship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.faang.user_service.entity.User;

import java.util.List;

@Repository
public interface MentorshipRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = """
            SELECT u.* FROM users u
            JOIN mentorship m ON u.id = m.mentee_id
            WHERE mentor_id = :mentorId
            """)
    List<User> getMenteesByMentorId(long mentorId);
}
