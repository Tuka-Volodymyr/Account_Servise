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

    @NotNull
    private String password;
    @JsonIgnore
    private ArrayList<String> roles;
    @JsonIgnore
    private String operation;
    @JsonIgnore
    private int FailedAttempts;
    @JsonIgnore
    @Transient
    private String availableRoles="ADMINISTRATOR_ACCOUNTANT_USER_AUDITOR";


    public ResponseUser returnUser() {
        return new ResponseUser(getId(),getName(),getLastname(),getEmail().toLowerCase(),getRoles());
    }
    public String getEmail() {
        return email.toLowerCase();
    }


}