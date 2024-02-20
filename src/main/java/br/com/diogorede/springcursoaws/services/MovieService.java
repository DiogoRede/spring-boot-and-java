package br.com.diogorede.springcursoaws.services;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diogorede.springcursoaws.controllers.MovieController;
import br.com.diogorede.springcursoaws.data.vo.v1.MovieVo;
import br.com.diogorede.springcursoaws.entities.Movie;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.exceptions.ResourceNotFoundException;
import br.com.diogorede.springcursoaws.mapper.DozerMapper;
import br.com.diogorede.springcursoaws.repositories.CategoryRepository;
import br.com.diogorede.springcursoaws.repositories.MovieRepository;
import br.com.diogorede.springcursoaws.repositories.PersonRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PersonRepository personRepository;

    public MovieVo findById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));
        var vo = DozerMapper.parseObject(entity, MovieVo.class);
        vo.add(linkTo(methodOn(MovieController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public List<MovieVo> findAll(){
        var movies = DozerMapper.parseListObjects(repository.findAll(), MovieVo.class);
        movies.stream()
            .forEach(movie -> {
                movie.add(linkTo(methodOn(MovieController.class).findById(movie.getKey())).withSelfRel());
            });
        return movies;
    }

    public MovieVo create(MovieVo movieVo){
        if(movieVo==null) throw new RequiredObjectIsNullException();

        var person = personRepository.findById(movieVo.getPerson().getId()).orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        var category = categoryRepository.findById(movieVo.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        var entity = DozerMapper.parseObject(movieVo, Movie.class);
        entity.setPerson(person);
        entity.setCategory(category);

        var vo = DozerMapper.parseObject(repository.save(entity), MovieVo.class);

        vo.add(linkTo(methodOn(MovieController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public MovieVo update(MovieVo movieVo){
        if(movieVo==null) throw new RequiredObjectIsNullException();

        var entity = DozerMapper.parseObject(movieVo, Movie.class);
        var vo = DozerMapper.parseObject(repository.save(entity), MovieVo.class);

        vo.add(linkTo(methodOn(MovieController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}