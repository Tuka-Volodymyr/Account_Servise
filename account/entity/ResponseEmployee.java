package account.entity;

import account.exceptions.employeeExceptions.WrongDateException;
import jakarta.validation.constraints.NotBlank;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class ResponseEmployee {
    private String name;
    private String lastname;
    private String period;

    private String salary;
    public ResponseEmployee(String name,String lastname,String period,String salary){
        this.name=name;
        this.lastname=lastname;
        String[] arrayOfDate=period.split("-");
        LocalDate localDate=LocalDate.of(Integer.parseInt(arrayOfDate[1]),Integer.parseInt(arrayOfDate[0]),3);
        this.period=String.format("%s%s-%s",String.valueOf(localDate.getMonth()).charAt(0),
                String.valueOf(localDate.getMonth()).substring(1).toLowerCase(),localDate.getYear());
        String firstStr = salary.substring(0, salary.length() - 2);
        String secondStr = salary.substring(salary.length() - 2);
        if(salary.length()<=2)firstStr="0";
        this.salary=String.format("%s dollar(s) %s cent(s)",firstStr,secondStr);
    }

    public String getPeriod() {
        return period;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }
}

