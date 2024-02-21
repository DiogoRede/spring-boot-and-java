package br.com.diogorede.springcursoaws.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.diogorede.springcursoaws.entities.Movie;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.exceptions.ResourceNotFoundException;
import br.com.diogorede.springcursoaws.repositories.CategoryRepository;
import br.com.diogorede.springcursoaws.repositories.MovieRepository;
import br.com.diogorede.springcursoaws.repositories.PersonRepository;
import br.com.diogorede.springcursoaws.services.MovieService;
import br.com.diogorede.springcursoaws.unittest.mapper.mocks.MockMovie;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    MockMovie input;

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockMovie();
        MockitoAnnotations.openMocks(input);
    }
    
    @Test
    void testFindAll(){
        List<Movie> list = input.mockEntityList();
        
        when(repository.findAll()).thenReturn(list);

        var result = service.findAll();
        assertEquals(14, result.size());
        assertNotNull(result);
        
        var movie1 = result.get(1);
        assertNotNull(movie1);
        assertNotNull(movie1.getKey());
        assertNotNull(movie1.getLinks());
        assertNotNull(movie1.getCategory());
        assertNotNull(movie1.getPerson());
        assertTrue(movie1.toString().contains("links: [</movie/v1/1>;rel=\"self\"]"));
        assertEquals("Description Test1",movie1.getDescription());
        assertEquals("Name Test1",movie1.getName());
        assertEquals(1,movie1.getPriority());
        assertEquals(1,movie1.getYear());

        var movie13 = result.get(13);
        assertNotNull(movie13);
        assertNotNull(movie13.getKey());
        assertNotNull(movie13.getLinks());
        assertNotNull(movie13.getCategory());
        assertNotNull(movie13.getPerson());
        System.out.println(movie13.toString());
        assertTrue(movie13.toString().contains("links: [</movie/v1/13>;rel=\"self\"]"));
        assertEquals("Description Test13",movie13.getDescription());
        assertEquals("Name Test13",movie13.getName());
        assertEquals(13,movie13.getPriority());
        assertEquals(13,movie13.getYear());

    }

    @Test
    void testFindById(){
        Movie entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.getCategory());
        assertNotNull(result.getPerson());
        assertTrue(result.toString().contains("links: [</movie/v1/1>;rel=\"self\"]"));
        assertEquals("Description Test1",result.getDescription());
        assertEquals("Name Test1",result.getName());
        assertEquals(1,result.getPriority());
        assertEquals(1,result.getYear());
    }

    @Test
    void testCreate(){
        var entity = input.mockEntity(1);

        var persisted = entity;
        persisted.setId(1L);

        var vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(vo.getPerson()));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(vo.getCategory()));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.getCategory());
        assertNotNull(result.getPerson());
        assertTrue(result.toString().contains("links: [</movie/v1/1>;rel=\"self\"]"));
        assertEquals("Description Test1",result.getDescription());
        assertEquals("Name Test1",result.getName());
        assertEquals(1,result.getPriority());
        assertEquals(1,result.getYear());
    }

    @Test
    void testCreateWithNullMovie(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateWithNullCategory(){
        var entity = input.mockVO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity.getPerson()));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.create(entity);
        });
        String expectedMessage = "Category not found!";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreateWithNullPerson(){
        var entity = input.mockVO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.create(entity);
        });
        String expectedMessage = "Person not found!";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdate(){
        var entity = input.mockEntity(1);

        var updated = entity;
        updated.setId(1L);

        var vo = input.mockVO(1);
        
        when(repository.save(entity)).thenReturn(updated);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.getCategory());
        assertNotNull(result.getPerson());
        assertTrue(result.toString().contains("links: [</movie/v1/1>;rel=\"self\"]"));
        assertEquals("Description Test1",result.getDescription());
        assertEquals("Name Test1",result.getName());
        assertEquals(1,result.getPriority());
        assertEquals(1,result.getYear());
    }

    @Test
    void testDelete(){
        var entity = input.mockEntity();
        entity.setId(1L);
        service.delete(1L);
    }
}