package account.controllers;
import account.entity.user.ChangeUserPassword;
import account.entity.user.User;
import account.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Validated
public class UserRestController {

    @Autowired
    public UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerToService(@Valid @RequestBody User user){
        return userService.registerAccount(user);
    }
    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePass(@AuthenticationPrincipal UserDetails details,@RequestBody ChangeUserPassword changeUserPassword){
        return userService.changePass(details,changeUserPassword.getNew_password());
    }

//    @PutMapping("/admin/user/role")
//    public ResponseEntity<?> changeRole(){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @DeleteMapping("/admin/user")
//    public ResponseEntity<?> deleteUser(){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @GetMapping("/admin/user")
//    public ResponseEntity<?> infoAllUsers(){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}


