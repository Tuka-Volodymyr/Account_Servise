package account.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private String action;
    private String subject;
    private String object;
    private String path;
    public Event(){

    }
    public Event(String date,String action,String subject,String object,String path){
        this.date=date;
        this.action=action;
        this.subject=subject;
        this.object=object;
        this.path=path;
    }


}
