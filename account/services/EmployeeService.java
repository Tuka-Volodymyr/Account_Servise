package account.services;

import account.entity.employee.Employee;
import account.entity.employee.ResponseEmployee;
import account.entity.user.User;

import account.exceptions.EmployeeNotFoundException400;


import account.exceptions.UserNotFoundException401;
import account.repository.EmployeeInfoRepository;
import account.repository.UserInfoRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Transactional
    public ResponseEntity<?> addPayment(ArrayList<Employee> listOfEmp){

        String availableDate="^(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})$";
        for(Employee emp:listOfEmp){
            if (!userInfoRepository.existsByEmailIgnoreCase(emp.getEmployee())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with specified email not found");
            }
            Optional<Employee> salaryAlreadyExist=employeeInfoRepository.findByEmployeeIgnoreCaseAndPeriod(emp.getEmployee(),emp.getPeriod());
            if(salaryAlreadyExist.isPresent())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Date of salary has already existed!");
            if(!emp.getPeriod().matches(availableDate))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong date!");
            checkSalary(emp);
            employeeInfoRepository.save(emp);
        }
        return new ResponseEntity<>(Map.of("status","Added successfully!"), HttpStatus.OK);
    }
    public ResponseEntity<?> changeSalary(Employee employee){
        Employee salaryAlreadyExist=employeeInfoRepository
                .findByEmployeeIgnoreCaseAndPeriod(employee.getEmployee(),employee.getPeriod())
                .orElseThrow(EmployeeNotFoundException400::new);
        checkSalary(employee);
        salaryAlreadyExist.setSalary(employee.getSalary());
        employeeInfoRepository.save(salaryAlreadyExist);
        return new ResponseEntity<>(Map.of("status","Updated successfully!"),HttpStatus.OK);
    }
    //some with period
    public ResponseEntity<?> getEmployeeSalaryWithPeriod(UserDetails userDetails, String period){
        User accountExist = userInfoRepository
                .findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(UserNotFoundException401::new);
        Employee employeeAlreadyUse = employeeInfoRepository
                .findByEmployeeIgnoreCaseAndPeriod(userDetails.getUsername(),period)
                .orElseThrow(EmployeeNotFoundException400::new);
        ResponseEmployee responseEmployee=new ResponseEmployee(
                accountExist.getName(),
                accountExist.getLastname(),
                employeeAlreadyUse.getPeriod(),
                String.valueOf(employeeAlreadyUse.getSalary())
        );
        return new ResponseEntity<>(responseEmployee,HttpStatus.OK);

    }
    //order is wrong
    public ResponseEntity<?> getAllEmployeeSalary(UserDetails userDetails) {
        User accountExist = userInfoRepository
                .findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(UserNotFoundException401::new);
        ArrayList<Employee> employeeArrayList = employeeInfoRepository.findByEmployeeIgnoreCaseOrderByIdDesc(userDetails.getUsername());
        ArrayList<ResponseEmployee> responseEmployeeArrayList = new ArrayList<>();
        for (Employee emp : employeeArrayList) {
            responseEmployeeArrayList.add(new ResponseEmployee(
                    accountExist.getName(),
                    accountExist.getLastname(),
                    emp.getPeriod(),
                    String.valueOf(emp.getSalary())
                    ));
        }
        return new ResponseEntity<>(responseEmployeeArrayList, HttpStatus.OK);
    }
    public void checkSalary(Employee emp){
        if (emp.getSalary()<0)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong salary!");;
    }
}
