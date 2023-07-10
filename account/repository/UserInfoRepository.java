package account.repository;

import account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByName(String name);
//    boolean existsByEmailIgnoreCase(String employee, YearMonth period);
    @Override
    <S extends User> S save(S entity);
}
