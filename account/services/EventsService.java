package account.services;

import account.entity.security.Event;
import account.entity.user.User;
import account.config.AuthenticationEventHandler;
import account.repository.EmployeeInfoRepository;
import account.repository.EventInfoRepository;
import account.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventsService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    private EventInfoRepository eventInfoRepository;
    public ResponseEntity<?> getAllEvents(){
        List<Event> listOfEvent=eventInfoRepository.findAllByOrderById();
        return new ResponseEntity<>(listOfEvent, HttpStatus.OK);
    }
    public void createUser(User user){
        Event event=new Event(
                LocalDate.now().toString(),
                "CREATE_USER",
                "Anonymous",
                user.getEmail(),
                request.getRequestURI()
        );
        eventInfoRepository.save(event);
    }
    public void grantRole(String role,User user){
        role=role.replace("ROLE_","");
        UserDetails userDetails=getUserDetails();
        Event event=new Event(
                LocalDate.now().toString(),
                "GRANT_ROLE",
                userDetails.getUsername(),
                String.format("Grant role %s to %s",role,user.getEmail()),
                "/api/admin/user/role"
        );
        eventInfoRepository.save(event);
    }
    public void removeRole(String role,User user){
        role=role.replace("ROLE_","");
        UserDetails userDetails=getUserDetails();
        Event event=new Event(
                LocalDate.now().toString(),
                "REMOVE_ROLE",
                userDetails.getUsername(),
                String.format("Remove role %s from %s",role,user.getEmail()),
                "/api/admin/user/role"
        );
        eventInfoRepository.save(event);
    }
    public void deleteUser(String email){
        UserDetails userDetails=getUserDetails();
        Event event=new Event(
                LocalDate.now().toString(),
                "DELETE_USER",
                userDetails.getUsername(),
                email,
                "/api/admin/user"
        );
        eventInfoRepository.save(event);
    }
    public void changePassword(User user){
        Event event=new Event(
                LocalDate.now().toString(),
                "CHANGE_PASSWORD",
                user.getEmail(),
                user.getEmail(),
                "/api/auth/changepass"
        );
        eventInfoRepository.save(event);
    }
    public void authenticationFailed(String email) {
        Event event=new Event(
                LocalDate.now().toString(),
                "LOGIN_FAILED",
                email,
                request.getRequestURI(),
                request.getRequestURI()
        );
        eventInfoRepository.save(event);
    }
    public void accessDenied() {
        UserDetails details = getUserDetails();
        Event event=new Event(
                LocalDate.now().toString(),
                "ACCESS_DENIED",
                details.getUsername(),
                request.getRequestURI(),
                request.getRequestURI()
        );
        eventInfoRepository.save(event);
    }
    public void lockUser(User user){
        if (user.getFailedAttempts() >= AuthenticationEventHandler.maxFailedAttempts)
            bruteForce(user.getEmail());
        Event event=new Event(
                LocalDate.now().toString(),
                "LOCK_USER",
                user.getEmail(),
                String.format("Lock user %s",user.getEmail()),
                request.getRequestURI()
        );
        user.setOperation("LOCK");
        user.setFailedAttempts(0);
        eventInfoRepository.save(event);
        userInfoRepository.save(user);
    }
    public void unlockUser(User user){
        User admin=userInfoRepository.findById(1).orElseThrow();
        Event event=new Event(
                LocalDate.now().toString(),
                "UNLOCK_USER",
                admin.getEmail(),
                String.format("Unlock user %s",user.getEmail()),
                request.getRequestURI()
        );
        user.setOperation("UNLOCK");
        user.setFailedAttempts(0);
        userInfoRepository.save(user);
        eventInfoRepository.save(event);
    }
    public void bruteForce(String email){
        Event event=new Event(
                LocalDate.now().toString(),
                "BRUTE_FORCE",
                email,
                request.getRequestURI(),
                request.getRequestURI()
        );
        eventInfoRepository.save(event);
    }
    public void increaseFailedAttemptsByUser(User user) {
        authenticationFailed(user.getEmail());
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        userInfoRepository.save(user);
    }
    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
