package account.repository;

import account.entity.security.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventInfoRepository extends JpaRepository<Event,Integer> {
    List<Event> findAllByOrderById();

}
