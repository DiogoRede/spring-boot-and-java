package br.com.diogorede.springcursoaws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.diogorede.springcursoaws.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    
    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
    void disablePerson(Long id);
}