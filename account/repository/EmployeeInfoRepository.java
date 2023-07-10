package account.repository;

import account.entity.Employee;
import account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<Employee,Integer> {
//    boolean existsByEmployeeIgnoreCaseAndPeriod(String employee, YearMonth period);

    Optional<Employee> findByEmployeeIgnoreCaseAndPeriod(String emp, String period);
    ArrayList<Employee> findByEmployeeIgnoreCaseOrderByIdDesc(String emp);
}
