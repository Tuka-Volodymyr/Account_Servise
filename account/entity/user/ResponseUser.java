package account.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;

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


    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

}
