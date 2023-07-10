package account.exceptions.employeeExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Date of salary has already existed!")
public class EmployeeExistException extends RuntimeException{
}
