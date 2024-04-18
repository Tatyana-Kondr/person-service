package ait.cohort34.person.dao;

import ait.cohort34.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
