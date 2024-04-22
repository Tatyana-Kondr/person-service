package ait.cohort34.person.dao;

import ait.cohort34.person.dto.PopulationDto;
import ait.cohort34.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    //@Query("select p  from Person p where p.name=?1") не обязательно использовать
    Stream<Person>findByNameIgnoreCase(String name);

    //@Query("select p  from Person p where p.address.city=:cityName")
    Stream<Person>findByAddressCity(@Param("cityName") String city);

    Stream<Person>findByBirthDateBetween(LocalDate from, LocalDate to);

    @Query("select new ait.cohort34.person.dto.PopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by  count (p) desc")
    List<PopulationDto> getPopulations();
}
