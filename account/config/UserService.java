package account.config;

import account.entity.User;
//import account.repository.UserInfoRepository;
import account.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user=userInfoRepository.findByEmailIgnoreCase(email);
        return user.map(UserInfoDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
