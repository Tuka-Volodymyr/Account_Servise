package account.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="USER_CUS")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;

    @NotBlank
    @Pattern(regexp = ".+@acme\\.com")
    private String email;

//    @Size(min = 12,message = "The password length must be at least 12 chars!")
    @NotNull
    private String password;
    @JsonIgnore
    private ArrayList<String> roles;
    @JsonIgnore
    @Transient
    private String availableRoles="ADMINISTRATOR_ACCOUNTANT_USER";


    public ResponseUser returnUser() {
        return new ResponseUser(getId(),getName(),getLastname(),getEmail().toLowerCase(),getRoles());

//        return "{id=" + id +
//                ", name='" + name + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", email='" + email + '\'' +
//                ", roles='"+ roles +'\'' +
//                '}';
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

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public String getAvailableRoles() {
        return availableRoles;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvailableRoles(String availableRoles) {
        this.availableRoles = availableRoles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}