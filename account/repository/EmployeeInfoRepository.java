package account.repository;

import account.entity.Employee;
import account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByEmployeeAndPeriodIgnoreCase(String emp,String period);
    ArrayList<Employee> findByEmployeeIgnoreCase(String emp);
}
