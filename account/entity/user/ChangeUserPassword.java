package account.entity.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ChangeUserPassword {
    private String new_password;
    public String getNew_password() {
        return new_password;
    }
}
