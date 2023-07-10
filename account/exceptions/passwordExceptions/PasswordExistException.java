package account.exceptions.passwordExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "The passwords must be different!")
public class PasswordExistException extends RuntimeException{
}
