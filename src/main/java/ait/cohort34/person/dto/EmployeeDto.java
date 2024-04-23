package ait.cohort34.person.dto;

import ait.cohort34.person.model.Employee;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmployeeDto extends PersonDto {

    String company;
    Integer salary;
   }
