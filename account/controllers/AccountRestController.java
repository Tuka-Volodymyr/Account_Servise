package account.controllers;
import account.entity.ChangeUserPassword;
import account.entity.Employee;
import account.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api")
@Validated
public class AccountRestController {

    @Autowired
    public AccountService accountService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerToService(@Valid @RequestBody User user){
        return accountService.registerAccount(user);
    }
    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePass(@AuthenticationPrincipal UserDetails details,@RequestBody ChangeUserPassword changeUserPassword){
        return accountService.changePass(details,changeUserPassword.getNew_password());
    }

    @PostMapping("/acct/payments")
    public ResponseEntity<?> uploadPayment(@RequestBody ArrayList<Employee> listOfEmployees){
        return accountService.addPayment(listOfEmployees);
    }
    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatesPayment(@RequestBody Employee employee){
        return accountService.changeSalary(employee);
    }
    @GetMapping(value="/empl/payment",params = "period")
    public ResponseEntity<?> getSalaryForPeriod(@AuthenticationPrincipal UserDetails details, @RequestParam  String period){
        return accountService.getEmployeeSalaryWithPeriod(details,period);
    }
    @GetMapping(value = "/empl/payment")
    public ResponseEntity<?> getAllSalary(@AuthenticationPrincipal UserDetails details){
        return accountService.getAllEmployeeSalary(details);
    }
    @PutMapping("/admin/user/role")
    public ResponseEntity<?> changeRole(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/admin/user")
    public ResponseEntity<?> deleteUser(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin/user")
    public ResponseEntity<?> infoAllUsers(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


