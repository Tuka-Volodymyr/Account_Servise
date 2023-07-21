package account.services;

import account.entity.admin.ChangeAccess;
import account.entity.admin.ChangeRole;
import account.entity.security.Event;
import account.entity.user.ResponseUser;
import account.entity.user.User;
import account.exceptions.general.DataEmptyException400;
import account.exceptions.user.UserNotFoundException404;
import account.repository.EmployeeInfoRepository;
import account.repository.EventInfoRepository;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class AdminService {
    @Autowired
    private EventInfoRepository eventInfoRepository;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    public ResponseEntity<?> changeRole(ChangeRole changeRole){
        if(changeRole==null)throw new DataEmptyException400();
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(changeRole.getUser())
                .orElseThrow(UserNotFoundException404::new);
        if(!accountAlreadyUse.getAvailableRoles().contains(changeRole.getRole()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found!");
        changeRole.setRole("ROLE_"+changeRole.getRole());
        ArrayList<String> listOfRole=accountAlreadyUse.getRoles();
        if(changeRole.getOperation().equals("GRANT")){
            if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR")
                    ||changeRole.getRole().equals("ROLE_ADMINISTRATOR"))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The user cannot combine administrative and business roles!");
            listOfRole.add(changeRole.getRole());
            Collections.sort(listOfRole);
            accountAlreadyUse.setRoles(listOfRole);
            eventsService.grantRole(changeRole.getRole(),accountAlreadyUse);
        }else {
            if(!accountAlreadyUse.getRoles().contains(changeRole.getRole()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user does not have a role!");
            if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't remove ADMINISTRATOR role!");
            if(accountAlreadyUse.getRoles().size()==1)
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The user must have at least one role!");
            listOfRole.remove(changeRole.getRole());
            accountAlreadyUse.setRoles(listOfRole);
            eventsService.removeRole(changeRole.getRole(),accountAlreadyUse);
        }
        userInfoRepository.save(accountAlreadyUse);
        return new ResponseEntity<>(accountAlreadyUse.returnUser(),HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(String userEmail){
        if(userEmail==null)throw new DataEmptyException400();
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(userEmail)
                .orElseThrow(UserNotFoundException404::new);
        if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't remove ADMINISTRATOR role!");
        eventsService.deleteUser(userEmail);
        userInfoRepository.delete(accountAlreadyUse);
        return new ResponseEntity<>(Map.of("user", userEmail,
                "status", "Deleted successfully!"),HttpStatus.OK);

    }

    public ResponseEntity<?> infoAllUsers(){
        List<User> listOfUsers=userInfoRepository.findAllByOrderById();
        ArrayList<ResponseUser> usersToString=new ArrayList<>();
        for(User user:listOfUsers)usersToString.add(user.returnUser());
        return new ResponseEntity<>(usersToString,HttpStatus.OK);
    }
    public ResponseEntity<?> changeAccess(ChangeAccess changeAccess){
        if(changeAccess==null)throw new DataEmptyException400();
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(changeAccess.getUser())
                .orElseThrow(UserNotFoundException404::new);
        if(changeAccess.getOperation().equals("LOCK")){
            if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't lock the ADMINISTRATOR!");
            eventsService.lockUser(accountAlreadyUse);
            return new ResponseEntity<>(Map.of("status",String.format("User %s locked!",
                    accountAlreadyUse.getEmail())),
                    HttpStatus.OK);
        } else {
            eventsService.unlockUser(accountAlreadyUse);
            return new ResponseEntity<>(Map.of("status",String.format("User %s unlocked!",
                    accountAlreadyUse.getEmail())),
                    HttpStatus.OK);
        }
    }
}
