package br.com.diogorede.springcursoaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.PersonVo;
import br.com.diogorede.springcursoaws.services.PersonService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @GetMapping("/{id}")
    public PersonVo findById(@PathVariable(value = "id") Long id) throws Exception {
        return personService.findById(id);
    }

    @GetMapping
    public List<PersonVo> findAll(){
        return personService.findAll();
    }

    @PostMapping
    public PersonVo create(@RequestBody PersonVo person) {
        return personService.create(person);
    }

    @PutMapping
    public PersonVo update(@RequestBody PersonVo person) {
        return personService.create(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") String id) {
        personService.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

}