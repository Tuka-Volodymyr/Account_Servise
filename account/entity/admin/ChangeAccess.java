package account.entity.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeAccess {
    private String user;
    private String operation;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user.toLowerCase();
    }

    public String getOperation() {
        return operation;
    }
}
