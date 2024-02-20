package br.com.diogorede.springcursoaws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diogorede.springcursoaws.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
    void deleteAllByPersonId(Long id);
} 