package br.com.diogorede.springcursoaws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.diogorede.springcursoaws.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User WHERE u.userName = :userName")
    User findByUsername(String userName);

}