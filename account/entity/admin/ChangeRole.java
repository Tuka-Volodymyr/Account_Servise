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

    public void setRole(String role) {
        this.role = role;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public String getOperation() {
        return operation;
    }
}
