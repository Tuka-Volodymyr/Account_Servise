package account.services;
import account.entity.user.User;
import account.exceptions.passwordExceptions.PasswordExistException;
import account.exceptions.passwordExceptions.PasswordInHackerDatabase;
import account.exceptions.passwordExceptions.PasswordIsShortException;
import account.exceptions.userExceptions.UserExistException;
import account.exceptions.userExceptions.UserNotFoundException;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;



@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

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


    public ResponseEntity<?> changePass(UserDetails userDetails,String new_pass){
        User accountAlreadyUse = userInfoRepository
                .findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
        checkPass(new_pass);
        if(passwordEncoder.matches(new_pass,accountAlreadyUse.getPassword()))throw new PasswordExistException();
        accountAlreadyUse.setPassword(passwordEncoder.encode(new_pass));
        userInfoRepository.save(accountAlreadyUse);
        return new ResponseEntity<>(Map.of("email", accountAlreadyUse.getEmail().toLowerCase(),
                "status", "The password has been updated successfully"),HttpStatus.OK);
    }

}

