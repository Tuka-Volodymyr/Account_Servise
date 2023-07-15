package account.repository;

import account.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<Employee,Integer> {
     boolean existsByEmployeeIgnoreCaseAndPeriod(String employee, String period);

    Optional<Employee> findByEmployeeIgnoreCaseAndPeriod(String emp, String period);
    ArrayList<Employee> findByEmployeeIgnoreCaseOrderByIdDesc(String emp);
}
