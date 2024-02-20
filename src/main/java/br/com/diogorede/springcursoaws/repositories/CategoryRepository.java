package br.com.diogorede.springcursoaws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diogorede.springcursoaws.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{}