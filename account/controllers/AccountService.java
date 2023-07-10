package account.controllers;
import account.entity.Employee;
import account.entity.ResponseEmployee;
import account.entity.User;
import account.exceptions.employeeExceptions.EmployeeExistException;
import account.exceptions.employeeExceptions.EmployeeNotFoundException;
import account.exceptions.employeeExceptions.SalaryNegativeException;
import account.exceptions.employeeExceptions.WrongDateException;
import account.exceptions.passwordExceptions.PasswordExistException;
import account.exceptions.passwordExceptions.PasswordInHackerDatabase;
import account.exceptions.passwordExceptions.PasswordIsShortException;
import account.exceptions.userExceptions.UserExistException;
import account.exceptions.userExceptions.UserNotFoundException;
import account.repository.EmployeeInfoRepository;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;



@Service
public class AccountService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerAccount(User user) {
        Optional<User> accountAlreadyUse = userInfoRepository.findByEmailIgnoreCase(user.getEmail());
        if(accountAlreadyUse.isPresent())throw new UserExistException();
        checkPass(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userInfoRepository.save(user);
        return new ResponseEntity<>(user.toString(),HttpStatus.OK);
    }
    public void checkPass(String pass){
        if(pass.length()<12)throw new PasswordIsShortException();
        String hackerData = "PasswordForJanuaryPasswordForFebruary" +
                "PasswordForMarchPasswordForAprilPasswordForMayPasswordForJune" +
                "PasswordForJulyPasswordForAugustPasswordForSeptemberPasswordForOctober" +
                "PasswordForNovemberPasswordForDecember";
        if(hackerData.contains(pass))throw new PasswordInHackerDatabase();
    }
    public void checkSalary(Employee emp){
        if (emp.getSalary()<0)throw new SalaryNegativeException();
    }

    public ResponseEntity<?> changePass(UserDetails userDetails,String new_pass){
        Optional<User> accountAlreadyUse = userInfoRepository.findByEmailIgnoreCase(userDetails.getUsername());
        if(accountAlreadyUse.isEmpty())throw new UserNotFoundException();
        checkPass(new_pass);
        if(passwordEncoder.matches(new_pass,accountAlreadyUse.get().getPassword()))throw new PasswordExistException();
        accountAlreadyUse.get().setPassword(passwordEncoder.encode(new_pass));
        userInfoRepository.save(accountAlreadyUse.get());
        return new ResponseEntity<>(Map.of("email", accountAlreadyUse.get().getEmail().toLowerCase(),
                "status", "The password has been updated successfully"),HttpStatus.OK);
    }
    public ResponseEntity<?> addPayment(ArrayList<Employee> listOfEmp){
        String availableDate="^(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})$";
        for(Employee emp:listOfEmp){
            Optional<User> accountExist = userInfoRepository.findByEmailIgnoreCase(emp.getEmployee());
            if(accountExist.isEmpty())throw new UserNotFoundException();
            Optional<Employee> salaryAlreadyExist=employeeInfoRepository.findByEmployeeAndPeriodIgnoreCase(emp.getEmployee(),emp.getPeriod());
            if(salaryAlreadyExist.isPresent())throw new EmployeeExistException();
            if(!emp.getPeriod().matches(availableDate))throw new WrongDateException();
            checkSalary(emp);
            employeeInfoRepository.save(emp);
        }
        return new ResponseEntity<>(Map.of("status","Added successfully!"),HttpStatus.OK);
    }
    public ResponseEntity<?> changeSalary(Employee employee){
        Optional<Employee> salaryAlreadyExist=employeeInfoRepository.
                findByEmployeeAndPeriodIgnoreCase(employee.getEmployee(),employee.getPeriod());
        if(salaryAlreadyExist.isEmpty())throw new EmployeeNotFoundException();
        checkSalary(employee);
        salaryAlreadyExist.get().setSalary(employee.getSalary());
        employeeInfoRepository.save(salaryAlreadyExist.get());
        return new ResponseEntity<>(Map.of("status","Updated successfully!"),HttpStatus.OK);
    }
    //some with period
    public ResponseEntity<?> getEmployeeSalaryWithPeriod(UserDetails userDetails,String period){
        Optional<User> accountExist = userInfoRepository.findByEmailIgnoreCase(userDetails.getUsername());
        if(accountExist.isEmpty())throw new UserNotFoundException();
        Optional<Employee> employeeAlreadyUse = employeeInfoRepository.
                findByEmployeeAndPeriodIgnoreCase(userDetails.getUsername(),period);
        if(employeeAlreadyUse.isEmpty())throw new EmployeeNotFoundException();
        ResponseEmployee responseEmployee=new ResponseEmployee(accountExist.get().getName(),accountExist.get().getLastname(),
                employeeAlreadyUse.get().getPeriod(),String.valueOf(employeeAlreadyUse.get().getSalary()));
        return new ResponseEntity<>(responseEmployee,HttpStatus.OK);

    }
    //order is wrong
    public ResponseEntity<?> getAllEmployeeSalary(UserDetails userDetails) {
        Optional<User> accountExist = userInfoRepository.findByEmailIgnoreCase(userDetails.getUsername());
        ArrayList<Employee> employeeArrayList = employeeInfoRepository.findByEmployeeIgnoreCase(userDetails.getUsername());
        ArrayList<ResponseEmployee> responseEmployeeArrayList = new ArrayList<>();
        for (Employee emp : employeeArrayList) {
            responseEmployeeArrayList.add(new ResponseEmployee(accountExist.get().getName(), accountExist.get().getLastname(), emp.getPeriod(), String.valueOf(emp.getSalary())));
        }
        return new ResponseEntity<>(responseEmployeeArrayList, HttpStatus.OK);
    }

}
