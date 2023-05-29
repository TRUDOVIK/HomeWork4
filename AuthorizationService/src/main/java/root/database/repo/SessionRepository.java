package root.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.database.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
}
