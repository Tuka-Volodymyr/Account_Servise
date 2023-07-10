package account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
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

    public Integer getId() {
        return id;
    }

    public long getSalary() {
        return salary;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
