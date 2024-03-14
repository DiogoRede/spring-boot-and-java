package br.com.diogorede.springcursoaws.integrationtests.controllers.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.diogorede.springcursoaws.configs.TestConfigs;
import br.com.diogorede.springcursoaws.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.diogorede.springcursoaws.integrationtests.vo.AccountCredentialsVo;
import br.com.diogorede.springcursoaws.integrationtests.vo.PersonVo;
import br.com.diogorede.springcursoaws.integrationtests.vo.TokenVo;
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
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException{
        AccountCredentialsVo user = new AccountCredentialsVo("diogo", "admin");

        var accessToken = given()
                        .basePath("/auth/signin")
                            .port(TestConfigs.SERVER_PORT)
                            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(user)
                            .when()
                        .post()
                            .then()
                                .statusCode(200)
                                    .extract()
                                    .body()
                                        .as(TokenVo.class)
                                    .getAccessToken();

        specification = new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                    .setBasePath("/api/v1/person")
                    .setPort(TestConfigs.SERVER_PORT)
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();
    }
    
    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        var content = given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                            .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
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

    // @Test
	// @Order(2)
	// public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {}

    // @Test
	// @Order(3)
	// public void testFindById() throws JsonMappingException, JsonProcessingException {}

    // @Test
	// @Order(4)
	// public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {}

    private void mockPerson() {
        person.setFirstName("Diogo");
        person.setLastName("Rede");
        person.setAddress("São Paulo");
        person.setGender("Male");
    }

}