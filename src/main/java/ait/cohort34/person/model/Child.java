package ait.cohort34.person.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Child extends Person{
    String kindergarten;

    public Child(Integer id, String name, LocalDate birthDate, Address address, String kindergarten) {
        super(id, name, birthDate, address);
        this.kindergarten = kindergarten;
    }

}
