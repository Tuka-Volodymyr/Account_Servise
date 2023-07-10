package account.exceptions.employeeExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Wrong date!")
public class WrongDateException extends RuntimeException{

}
