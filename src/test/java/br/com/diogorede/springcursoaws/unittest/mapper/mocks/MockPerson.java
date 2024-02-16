package br.com.diogorede.springcursoaws.unittest.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.diogorede.springcursoaws.data.vo.v1.PersonVo;
import br.com.diogorede.springcursoaws.entities.Person;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }
    
    public PersonVo mockVO() {
        return mockVO(0);
    }
    
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonVo> mockVOList() {
        List<PersonVo> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public PersonVo mockVO(Integer number) {
        PersonVo person = new PersonVo();
        person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setKey(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}
