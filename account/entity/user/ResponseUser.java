package account.entity.user;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class ResponseUser {
    private Integer id;

    private String name;

    private String lastname;

    private String email;
    private ArrayList<String> roles;
    public ResponseUser(Integer id,String name,String lastname,String email,ArrayList<String> roles){
        this.id=id;
        this.name=name;
        this.lastname=lastname;
        this.email=email;
        this.roles=roles;
    }


}
