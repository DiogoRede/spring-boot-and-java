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

import br.com.diogorede.springcursoaws.data.vo.v1.CategoryVo;
import br.com.diogorede.springcursoaws.entities.Category;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.repositories.CategoryRepository;
import br.com.diogorede.springcursoaws.services.CategoryService;
import br.com.diogorede.springcursoaws.unittest.mapper.mocks.MockCategory;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    MockCategory input;

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockCategory();
        MockitoAnnotations.openMocks(input);
    }

    @Test
    void testFindById() {
        Category category = input.mockEntity(1);
        category.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(category));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/v1/category/1>;rel=\"self\"]"));
        assertEquals("Name Test1",result.getName());
    }

    @Test
    void testUpdateWithNullCategory(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testFindAll(){
        List<Category> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var categories = service.findAll();
        assertNotNull(categories);
        assertEquals(14, categories.size());

        var category1 = categories.get(1);
        assertNotNull(category1);
        assertNotNull(category1.getKey());
        assertNotNull(category1.getLinks());
        assertTrue(category1.toString().contains("links: [</api/v1/category/1>;rel=\"self\"]"));
        assertEquals("Name Test1",category1.getName());

        var category13 = categories.get(13);
        assertNotNull(category13);
        assertNotNull(category13.getKey());
        assertNotNull(category13.getLinks());
        assertTrue(category13.toString().contains("links: [</api/v1/category/13>;rel=\"self\"]"));
        assertEquals("Name Test13",category13.getName());
    }

    @Test
    void testCreate(){
        var entity = input.mockEntity(1);

        var persisted = entity;
        persisted.setId(1L);

        var vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);
        
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/v1/category/1>;rel=\"self\"]"));
        assertEquals("Name Test1",result.getName());
    }

    @Test
    void testCreateWithNullCategory(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate(){
        var entity = input.mockEntity(1);
        entity.setId(1L);

        var updated = entity;
        updated.setId(1L);

        CategoryVo vo = input.mockVO(1);
        vo.setKey(1L);

        // when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(updated);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/v1/category/1>;rel=\"self\"]"));
        assertEquals("Name Test1",result.getName());
    }

    @Test
    void testDelete(){
        Category entity = input.mockEntity(1);
        entity.setId(1L);

        service.delete(1L);
    }
    
}