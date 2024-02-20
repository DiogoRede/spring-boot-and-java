package br.com.diogorede.springcursoaws.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.diogorede.springcursoaws.controllers.PersonController;
import br.com.diogorede.springcursoaws.data.vo.v1.PersonVo;
import br.com.diogorede.springcursoaws.entities.Person;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.exceptions.ResourceNotFoundException;
import br.com.diogorede.springcursoaws.mapper.DozerMapper;
import br.com.diogorede.springcursoaws.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<PersonVo> findAll(){
        var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVo.class);
        persons
            .stream()
            .forEach(person -> {
                try {
                    person.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        return persons;
    }

    public PersonVo create(PersonVo personVo){
        if(personVo==null) throw new RequiredObjectIsNullException();

        var entity = DozerMapper.parseObject(personVo, Person.class);
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVo.class);
        try {
            vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public PersonVo update(PersonVo person){
        if(person==null) throw new RequiredObjectIsNullException();

        var entity = personRepository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("Id not found!"));

        entity.setAddress(person.getAddress());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVo.class);

        try {
            vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vo;
    }
    
    public PersonVo findById(Long id){
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found!"));

        PersonVo vo = DozerMapper.parseObject(entity, PersonVo.class);
        try {
            vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public void delete(Long id){
        personRepository.deleteById(id);
    }
}