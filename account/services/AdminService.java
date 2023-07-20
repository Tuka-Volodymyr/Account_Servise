package account.services;

import account.entity.admin.ChangeRole;
import account.entity.user.ResponseUser;
import account.entity.user.User;
import account.exceptions.UserNotFoundException404;

import account.repository.EmployeeInfoRepository;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    //WRITE EXCEPTION!!!
    public ResponseEntity<?> changeRole(ChangeRole changeRole){

        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(changeRole.getUser())
                .orElseThrow(UserNotFoundException404::new);
//        User admin=userInfoRepository.findByRoles("ROLE_ADMINISTRATOR")
//                .orElseThrow(UserNotFoundException404::new);
        if(!accountAlreadyUse.getAvailableRoles().contains(changeRole.getRole()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found!");
        changeRole.setRole("ROLE_"+changeRole.getRole());
        ArrayList<String> listOfRole=accountAlreadyUse.getRoles();
        if(changeRole.getOperation().equals("GRANT")){
            if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR")
                    ||changeRole.getRole().equals("ROLE_ADMINISTRATOR"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user cannot combine administrative and business roles!");
            listOfRole.add(changeRole.getRole());
            Collections.sort(listOfRole);
            accountAlreadyUse.setRoles(listOfRole);
        }else {
            if(!accountAlreadyUse.getRoles().contains(changeRole.getRole()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user does not have a role!");
            if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't remove ADMINISTRATOR role!");
            if(accountAlreadyUse.getRoles().size()==1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user must have at least one role!");
            listOfRole.remove(changeRole.getRole());
            accountAlreadyUse.setRoles(listOfRole);
        }
        userInfoRepository.save(accountAlreadyUse);
        return new ResponseEntity<>(accountAlreadyUse.returnUser(),HttpStatus.OK);
    }
    //WRITE EXCEPTION!!!
    public ResponseEntity<?> deleteUser(String userEmail){
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(userEmail)
                .orElseThrow(UserNotFoundException404::new);
        if(accountAlreadyUse.getRoles().contains("ROLE_ADMINISTRATOR"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't remove ADMINISTRATOR role!");
        userInfoRepository.delete(accountAlreadyUse);
        return new ResponseEntity<>(Map.of("user", userEmail,
                "status", "Deleted successfully!"),HttpStatus.OK);

    }
    //WRITE EXCEPTION!!!
    public ResponseEntity<?> infoAllUsers(){
        List<User> listOfUsers=userInfoRepository.findAllByOrderById();
        ArrayList<ResponseUser> usersToString=new ArrayList<>();
        for(User user:listOfUsers)usersToString.add(user.returnUser());
        return new ResponseEntity<>(usersToString,HttpStatus.OK);
    }
}
