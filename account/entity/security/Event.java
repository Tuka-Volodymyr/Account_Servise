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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(String date) {
        this.date = date;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getAction() {
        return action;
    }

    public String getDate() {

        return date;
    }

    public String getObject() {
        return object;
    }

    public String getSubject() {
        return subject;
    }
}
