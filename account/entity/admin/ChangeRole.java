package account.entity.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeRole {
    @NotBlank
    private String user;
    @NotBlank
    private String role;
    @NotBlank
    private String operation;


    public String getUser() {
        return user.toLowerCase();
    }


}
