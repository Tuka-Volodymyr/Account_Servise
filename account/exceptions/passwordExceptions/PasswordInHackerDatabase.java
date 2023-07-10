package account.exceptions.passwordExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "The password is in the hacker's database!")
public class PasswordInHackerDatabase extends RuntimeException{ }
