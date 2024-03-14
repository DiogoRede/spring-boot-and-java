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

import br.com.diogorede.springcursoaws.data.vo.v1.PersonVo;
import br.com.diogorede.springcursoaws.entities.Person;
import br.com.diogorede.springcursoaws.exceptions.RequiredObjectIsNullException;
import br.com.diogorede.springcursoaws.repositories.PersonRepository;
import br.com.diogorede.springcursoaws.services.PersonService;
import br.com.diogorede.springcursoaws.unittest.mapper.mocks.MockPerson;


@TestInstance(Lifecycle.PER_CLASS) /* Ciclo de vida do teste */
@ExtendWith(MockitoExtension.class) /* Extender Mockito */
public class PersonServiceTest {

        MockPerson input; // Criando mock (não instanciado)

        // Mock PersonService
        @InjectMocks
        private PersonService service;

        @Mock
        PersonRepository repository;

        @BeforeEach
        void setUpMocks() throws Exception{
                // instancia mock
                input = new MockPerson();
                MockitoAnnotations.openMocks(input);
        }

        @Test
        void testFindById() {
                Person person = input.mockEntity(1);
                person.setId(1L);

                when(repository.findById(1L)).thenReturn(Optional.of(person));

                var result = service.findById(1L);
                assertNotNull(result);
                assertNotNull(result.getKey());
                assertNotNull(result.getLinks());
                assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
                assertEquals("Addres Test1", result.getAddress());
                assertEquals("First Name Test1", result.getFirstName());
                assertEquals("Last Name Test1", result.getLastName());
                assertEquals("Female", result.getGender());
        }

        @Test
        void testCreate() {
                Person entity = input.mockEntity(1);

                Person persisted = entity;
                persisted.setId(1L);

                PersonVo vo = input.mockVO(1);
                vo.setKey(1L);

                when(repository.save(entity)).thenReturn(persisted);

                var result = service.create(vo);
                assertNotNull(result);
                assertNotNull(result.getKey());
                assertNotNull(result.getLinks());
                assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
                assertEquals("Addres Test1", result.getAddress());
                assertEquals("First Name Test1", result.getFirstName());
                assertEquals("Last Name Test1", result.getLastName());
                assertEquals("Female", result.getGender());
        }

        @Test
        void testCreateWithNullPerson() {
                Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
                        service.create(null);
                });

                String expectedMessage = "It is not allowed to persist a null object!";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
        }
        
        @Test
        void testUpdate() {
                Person entity = input.mockEntity(1);
                entity.setId(1L);

                Person persisted = entity;
                persisted.setId(1L);

                PersonVo vo = input.mockVO(1);
                vo.setKey(1L);

                when(repository.findById(1L)).thenReturn(Optional.of(entity));
                when(repository.save(entity)).thenReturn(persisted);

                var result = service.update(vo);

                assertNotNull(result);
                assertNotNull(result.getKey());
                assertNotNull(result.getLinks());
                System.out.println(result.toString());
                assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
                assertEquals("Addres Test1", result.getAddress());
                assertEquals("First Name Test1", result.getFirstName());
                assertEquals("Last Name Test1", result.getLastName());
                assertEquals("Female", result.getGender());
        }

        @Test
        void testUpdateWithNullPerson() {
                Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
                        service.update(null);
                });

                String expectedMessage = "It is not allowed to persist a null object!";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void testDelete() {
                Person entity = input.mockEntity();
                entity.setId(1L);

                // Mockito diz que não é necessario esse when
                //when(repository.findById(1L)).thenReturn(Optional.of(entity));

                service.delete(1L);
        }

        @Test
        void testFindAll() {
                List<Person> list = input.mockEntityList();

                when(repository.findAll()).thenReturn(list);

                var persons = service.findAll();

                assertNotNull(persons);
                assertEquals(14, persons.size());

                var person1 = persons.get(1);

                assertNotNull(person1);
                assertNotNull(person1.getKey());
                assertNotNull(person1.getLinks());
                assertTrue(person1.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
                assertEquals("Addres Test1", person1.getAddress());
                assertEquals("First Name Test1", person1.getFirstName());
                assertEquals("Last Name Test1", person1.getLastName());
                assertEquals("Female", person1.getGender());

                var person12 = persons.get(12);
                
                assertNotNull(person12);
                assertNotNull(person12.getKey());
                assertNotNull(person12.getLinks());
                assertTrue(person12.toString().contains("links: [</api/v1/person/12>;rel=\"self\"]"));
                assertEquals("Addres Test12", person12.getAddress());
                assertEquals("First Name Test12", person12.getFirstName());
                assertEquals("Last Name Test12", person12.getLastName());
                assertEquals("Male", person12.getGender());
        }

}