package account.entity.employee;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Getter
@Setter
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

}

