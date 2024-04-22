package ait.cohort34.person.service;

import ait.cohort34.person.dto.AddressDto;
import ait.cohort34.person.dto.PersonDto;
import ait.cohort34.person.dto.PopulationDto;

public interface PersonService  {
    Boolean addPerson(PersonDto personDto);
    PersonDto findPersonById(Integer id);
    PersonDto removePersonById(Integer id);
    PersonDto updatePersonName(Integer id, String name);
    PersonDto updatePersonAddress(Integer id, AddressDto addressDto);
    PersonDto[] findPersonsByCity(String city);
    PersonDto[] findPersonsByAge(Integer ageFrom, Integer ageTo);
    PersonDto[] findPersonsByName(String name);
    Iterable<PopulationDto> getPopulations();
}
