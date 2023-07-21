package account.config;

import account.entity.user.User;
import account.exceptions.user.UserNotFoundException401;
import account.repository.UserInfoRepository;
import account.services.EventsService;
import account.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationEventHandler {

    public static final int maxFailedAttempts = 5;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        UserDetails details = (UserDetails) success.getAuthentication().getPrincipal();
        userService.resetUserAttempts(details.getUsername());
    }
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        String email = (String) failures.getAuthentication().getPrincipal();
        Optional<User> userOptional = userInfoRepository.findByEmailIgnoreCase(email);
        if (userOptional.isPresent()) {
            User user=userOptional.get();
            if(user.getOperation().equals("LOCK")){
                return;
            }
            eventsService.increaseFailedAttemptsByUser(user);
            if (user.getFailedAttempts() >= maxFailedAttempts&&!user.getRoles().contains("ROLE_ADMINISTRATOR")) {
                eventsService.lockUser(user);
            }
        } else {
            eventsService.authenticationFailed(email);
        }
    }
}
