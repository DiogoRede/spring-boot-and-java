package br.com.diogorede.springcursoaws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diogorede.springcursoaws.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{}