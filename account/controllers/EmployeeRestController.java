package account.controllers;

import account.entity.employee.Employee;
import account.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@Validated
public class EmployeeRestController {
    @Autowired
    public EmployeeService employeeService;
    @PreAuthorize("hasAnyRole('ROLE_ACCOUNTANT')")
    @PostMapping("/acct/payments")
    public ResponseEntity<?> uploadPayment(@RequestBody(required = false) ArrayList<Employee> listOfEmployees){
        return employeeService.addPayment(listOfEmployees);
    }
    @PreAuthorize("hasAnyRole('ROLE_ACCOUNTANT')")
    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatesPayment(@RequestBody Employee employee){
        return employeeService.changeSalary(employee);
    }
    @PreAuthorize("hasAnyRole('ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping(value="/empl/payment",params = "period")
    public ResponseEntity<?> getSalaryForPeriod(@AuthenticationPrincipal UserDetails details, @RequestParam  String period){
        return employeeService.getEmployeeSalaryWithPeriod(details,period);
    }
    @PreAuthorize("hasAnyRole('ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping(value = "/empl/payment")
    public ResponseEntity<?> getAllSalary(@AuthenticationPrincipal UserDetails details) {
        return employeeService.getAllEmployeeSalary(details);
    }
}
