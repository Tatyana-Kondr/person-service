package ait.cohort34.person.service;

import ait.cohort34.person.dao.PersonRepository;
import ait.cohort34.person.dto.AddressDto;
import ait.cohort34.person.dto.PersonDto;
import ait.cohort34.person.dto.PopulationDto;
import ait.cohort34.person.dto.exceptions.PersonNotFoundException;
import ait.cohort34.person.model.Address;
import ait.cohort34.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
    final PersonRepository personRepository;
    final ModelMapper modelMapper;

    @Override
    public Boolean addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            return false;
        }
        personRepository.save(modelMapper.map(personDto, Person.class));
        return true;
    }

    @Override
    public PersonDto findPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto removePersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePersonName(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if(person.getName() != null) {
            person.setName(name);
        }
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if(addressDto.getCity() != null) {
            Address newAddress = new Address(addressDto.getCity(), addressDto.getStreet(), addressDto.getBuilding());
            person.setAddress(newAddress);
        }
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public Iterable<PersonDto> findPersonsByCity(String city) {
        return null;
    }

    @Override
    public Iterable<PersonDto> findPersonsByAge(Integer ageFrom, Integer ageTo) {
        return null;
    }

    @Override
    public Iterable<PersonDto> findPersonsByName(String name) {
        return null;
    }

    @Override
    public Iterable<PopulationDto> getPopulations() {
        return null;
    }
}
