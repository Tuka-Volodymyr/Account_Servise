package account.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


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

    @Override
    public String toString() {
        return  "{id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}