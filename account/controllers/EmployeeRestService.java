package account.controllers;

import account.entity.employee.Employee;
import account.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@Validated
public class EmployeeRestService {
    @Autowired
    public EmployeeService employeeService;
    @PostMapping("/acct/payments")
    public ResponseEntity<?> uploadPayment(@RequestBody ArrayList<Employee> listOfEmployees){
        return employeeService.addPayment(listOfEmployees);
    }
    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatesPayment(@RequestBody Employee employee){
        return employeeService.changeSalary(employee);
    }
    @GetMapping(value="/empl/payment",params = "period")
    public ResponseEntity<?> getSalaryForPeriod(@AuthenticationPrincipal UserDetails details, @RequestParam  String period){
        return employeeService.getEmployeeSalaryWithPeriod(details,period);
    }
    @GetMapping(value = "/empl/payment")
    public ResponseEntity<?> getAllSalary(@AuthenticationPrincipal UserDetails details) {
        return employeeService.getAllEmployeeSalary(details);
    }
}
