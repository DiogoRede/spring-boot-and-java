package br.com.diogorede.springcursoaws.integrationtests.controllers.withjson;

import static io.restassured.RestAssured.given;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.diogorede.springcursoaws.configs.TestConfigs;
import br.com.diogorede.springcursoaws.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.diogorede.springcursoaws.integrationtests.vo.PersonVo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest{

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonVo person;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVo();
    }
    
    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        specification = new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:3000")
                    .setBasePath("/person/v1")
                    .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();

        var content = given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                            .body(person)
                            .when()
                            .post()
                        .then()
                            .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        PersonVo createdPerson = objectMapper.readValue(content, PersonVo.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId()>0);

        assertEquals("Diogo",createdPerson.getFirstName());
        assertEquals("Rede",createdPerson.getLastName());
        assertEquals("São Paulo",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
    }

    private void mockPerson() {
        person.setFirstName("Diogo");
        person.setLastName("Rede");
        person.setAddress("São Paulo");
        person.setGender("Male");
    }

}