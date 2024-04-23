package ait.cohort34.person.controller;

import ait.cohort34.person.dto.*;
import ait.cohort34.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    final PersonService personService;

    @PostMapping
    public Boolean addPerson(@RequestBody PersonDto personDto){
        return personService.addPerson(personDto);
    }

    @GetMapping("/{id}")
    public PersonDto findPersonById(@PathVariable Integer id) {
        return personService.findPersonById(id);
    }

    @DeleteMapping("/{id}")
    public PersonDto removePersonById(@PathVariable Integer id) {
        return personService.removePersonById(id);
    }

    @PutMapping("/{id}/name/{name}")
    public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String name) {
        return personService.updatePersonName(id, name);
    }

    @PutMapping("/{id}/address")
    public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
        return personService.updatePersonAddress(id, addressDto);
    }

    @GetMapping("/city/{city}")
    public PersonDto[] findPersonsByCity(@PathVariable String city) {
        return personService.findPersonsByCity(city);
    }

    @GetMapping("ages/{ageFrom}/{ageTo}")
    public PersonDto[] findPersonsByAge(@PathVariable Integer ageFrom, @PathVariable Integer ageTo) {
        return personService.findPersonsByAge(ageFrom, ageTo);
    }

    @GetMapping("/name/{name}")
    public PersonDto[] findPersonsByName(@PathVariable String name) {
        return personService.findPersonsByName(name);
    }

    @GetMapping("/population/city")
    public Iterable<PopulationDto> getPopulations() {
        return personService.getPopulations();
    }
    @GetMapping("/children")
    public ChildDto[] findAllChildren() {
        return personService.getChildren();
    }

    @GetMapping("/salary/{minSalary}/{maxSalary}")
    public EmployeeDto[] findEmployeesBySalary(@PathVariable Integer minSalary, @PathVariable Integer maxSalary) {
        return personService.findEmployeesBySalary(minSalary, maxSalary);
    }

}
