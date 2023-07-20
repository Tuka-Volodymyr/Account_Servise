package account.repository;

import account.entity.employee.Employee;
import account.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByRoles(String role);
    List<User> findAllByOrderById();

    boolean existsByEmailIgnoreCase(String email);
    @Override
    <S extends User> S save(S entity);
}
