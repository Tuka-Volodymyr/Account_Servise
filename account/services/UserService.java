package account.services;
import account.entity.user.User;
import account.exceptions.UserNotFoundException401;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerAccount(User user) {
        //some wrong
        ArrayList<String> listOfRole=new ArrayList<>();
        List<User> checkIfTableIsEmpty=userInfoRepository.findAll();
        if(checkIfTableIsEmpty.isEmpty()){
            listOfRole.add("ROLE_ADMINISTRATOR");
            user.setRoles(listOfRole);
        }else {
            listOfRole.add("ROLE_USER");
            user.setRoles(listOfRole);
        }
        Optional<User> accountAlreadyUse = userInfoRepository.findByEmailIgnoreCase(user.getEmail());
        if(accountAlreadyUse.isPresent())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User exist!");
        checkPass(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userInfoRepository.save(user);
        return new ResponseEntity<>(user.returnUser(),HttpStatus.OK);
    }
    public void checkPass(String pass){
        if(pass.length()<12)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password length must be 12 chars minimum!");
        String hackerData = "PasswordForJanuaryPasswordForFebruary" +
                "PasswordForMarchPasswordForAprilPasswordForMayPasswordForJune" +
                "PasswordForJulyPasswordForAugustPasswordForSeptemberPasswordForOctober" +
                "PasswordForNovemberPasswordForDecember";
        if(hackerData.contains(pass))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The password is in the hacker's database!");
    }


    public ResponseEntity<?> changePass(UserDetails userDetails,String new_pass){
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(UserNotFoundException401::new);
        checkPass(new_pass);
        if(passwordEncoder.matches(new_pass,accountAlreadyUse.getPassword()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The passwords must be different!");
        accountAlreadyUse.setPassword(passwordEncoder.encode(new_pass));
        userInfoRepository.save(accountAlreadyUse);
        return new ResponseEntity<>(Map.of("email", accountAlreadyUse.getEmail().toLowerCase(),
                "status", "The password has been updated successfully"),HttpStatus.OK);
    }

}

