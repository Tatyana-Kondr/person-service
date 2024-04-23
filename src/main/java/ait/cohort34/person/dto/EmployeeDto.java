package ait.cohort34.person.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmployeeDto {
    Integer id;
    String name;
    LocalDate birthDate;
    AddressDto address;
    String company;
    Integer salary;
    String type;
}
