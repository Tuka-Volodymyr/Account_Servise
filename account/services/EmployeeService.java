package account.services;

import account.entity.employee.Employee;
import account.entity.employee.ResponseEmployee;
import account.entity.user.User;
import account.exceptions.employeeExceptions.EmployeeExistException;
import account.exceptions.employeeExceptions.EmployeeNotFoundException;
import account.exceptions.employeeExceptions.SalaryNegativeException;
import account.exceptions.employeeExceptions.WrongDateException;
import account.exceptions.userExceptions.UserNotFoundException;
import account.repository.EmployeeInfoRepository;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class EmployeeService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    public ResponseEntity<?> addPayment(ArrayList<Employee> listOfEmp){
        String availableDate="^(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})$";
        for(Employee emp:listOfEmp){
            User accountExist = userInfoRepository
                    .findByEmailIgnoreCase(emp.getEmployee())
                    .orElseThrow(UserNotFoundException::new);
            Optional<Employee> salaryAlreadyExist=employeeInfoRepository.findByEmployeeIgnoreCaseAndPeriod(emp.getEmployee(),emp.getPeriod());
            if(salaryAlreadyExist.isPresent())throw new EmployeeExistException();
            if(!emp.getPeriod().matches(availableDate))throw new WrongDateException();
            checkSalary(emp);
            employeeInfoRepository.save(emp);
        }
        return new ResponseEntity<>(Map.of("status","Added successfully!"), HttpStatus.OK);
    }
    public ResponseEntity<?> changeSalary(Employee employee){
        Employee salaryAlreadyExist=employeeInfoRepository
                .findByEmployeeIgnoreCaseAndPeriod(employee.getEmployee(),employee.getPeriod())
                .orElseThrow(EmployeeNotFoundException::new);
        checkSalary(employee);
        salaryAlreadyExist.setSalary(employee.getSalary());
        employeeInfoRepository.save(salaryAlreadyExist);
        return new ResponseEntity<>(Map.of("status","Updated successfully!"),HttpStatus.OK);
    }
    //some with period
    public ResponseEntity<?> getEmployeeSalaryWithPeriod(UserDetails userDetails, String period){
        User accountExist = userInfoRepository
                .findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
        Employee employeeAlreadyUse = employeeInfoRepository
                .findByEmployeeIgnoreCaseAndPeriod(userDetails.getUsername(),period)
                .orElseThrow(EmployeeNotFoundException::new);
        ResponseEmployee responseEmployee=new ResponseEmployee(
                accountExist.getName(),
                accountExist.getLastname(),
                employeeAlreadyUse.getPeriod(),
                String.valueOf(employeeAlreadyUse.getSalary()));
        return new ResponseEntity<>(responseEmployee,HttpStatus.OK);

    }
    //order is wrong
    public ResponseEntity<?> getAllEmployeeSalary(UserDetails userDetails) {
        Optional<User> accountExist = userInfoRepository.findByEmailIgnoreCase(userDetails.getUsername());
        ArrayList<Employee> employeeArrayList = employeeInfoRepository.findByEmployeeIgnoreCaseOrderByIdDesc(userDetails.getUsername());
        ArrayList<ResponseEmployee> responseEmployeeArrayList = new ArrayList<>();
        for (Employee emp : employeeArrayList) {
            responseEmployeeArrayList.add(new ResponseEmployee(
                    accountExist.get().getName(),
                    accountExist.get().getLastname(),
                    emp.getPeriod(),
                    String.valueOf(emp.getSalary())));
        }
        return new ResponseEntity<>(responseEmployeeArrayList, HttpStatus.OK);
    }
    public void checkSalary(Employee emp){
        if (emp.getSalary()<0)throw new SalaryNegativeException();
    }
}
