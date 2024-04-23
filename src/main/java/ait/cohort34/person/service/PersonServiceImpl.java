package ait.cohort34.person.service;

import ait.cohort34.person.dao.PersonRepository;
import ait.cohort34.person.dto.*;
import ait.cohort34.person.dto.exceptions.PersonNotFoundException;
import ait.cohort34.person.model.Address;
import ait.cohort34.person.model.Child;
import ait.cohort34.person.model.Employee;
import ait.cohort34.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
    final PersonRepository personRepository;
    final ModelMapper modelMapper;
    final PersonModelDtoMapper mapper;

    @Transactional
    @Override
    public Boolean addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            return false;
        }
        personRepository.save(mapper.mapToModel(personDto));
        return true;
    }

    @Override
    public PersonDto findPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return mapper.mapToDto(person);
    }

    @Transactional
    @Override
    public PersonDto removePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return mapper.mapToDto(person);
    }

    @Transactional
    @Override
    public PersonDto updatePersonName(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if(person.getName() != null) {
            person.setName(name);
        }
        //personRepository.save(person); // в транзакционных методах save не нужен
        return mapper.mapToDto(person);
    }

    @Transactional  //что бы не могли одновременно менять данные в базе
    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if(addressDto.getCity() != null) {
            Address newAddress = new Address(addressDto.getCity(), addressDto.getStreet(), addressDto.getBuilding());
            person.setAddress(newAddress);
        }
        //personRepository.save(person);
        return mapper.mapToDto(person);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto[] findPersonsByCity(String city) {
        return personRepository.findByAddressCityIgnoreCase(city)
                .map(mapper::mapToDto)
                .toArray(PersonDto[]::new);
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDto[] findPersonsByAge(Integer minAge, Integer maxAge) {
        LocalDate from = LocalDate.now().minusYears(maxAge);
        LocalDate to = LocalDate.now().plusYears(minAge);
        return personRepository.findByBirthDateBetween(from, to)
                .map(mapper::mapToDto)
                .toArray(PersonDto[]::new);
    }

    @Transactional(readOnly = true) //чтение проходит паралельно, а запись по очереди
    @Override
    public PersonDto[] findPersonsByName(String name) {
        return personRepository.findByNameIgnoreCase(name)
                .map(mapper::mapToDto)
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<PopulationDto> getPopulations() {
        return personRepository.getCitiesPopulations();
    }

    @Transactional(readOnly = true)
    @Override
    public ChildDto[] getChildren() {
        return personRepository.findChildBy()
                .map(c -> modelMapper.map(c, ChildDto.class))
                .toArray(ChildDto[]::new);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto[] findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
        return personRepository.findEmployeesBySalaryBetween(minSalary, maxSalary)
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .toArray(EmployeeDto[]::new);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if(personRepository.count() == 0){
            Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
                    new Address("Berlin", "Purim", 18));
            Child child = new Child(2000, "Karl", LocalDate.of(2018, 3, 11),
                    new Address("Hamburg", "Hauptstrasse", 5), "Sunny");
            Employee employee = new Employee(3000, "Mary", LocalDate.of(1995, 11, 23),
                    new Address("Bremen", "Pappelstrasse", 28), "Motorola", 4500);
            personRepository.save(person);
            personRepository.save(child);
            personRepository.save(employee);
        }
    }
}
