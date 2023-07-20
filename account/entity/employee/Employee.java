package account.entity.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="EMPLOYEES")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String employee;
    @NotBlank
    private String period;

    private long salary;

    public String getPeriod() {
        return period;
    }

    public Integer getId() {
        return id;
    }

    public String getEmployee() {
        return employee;
    }

    public long getSalary() {
        return salary;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
