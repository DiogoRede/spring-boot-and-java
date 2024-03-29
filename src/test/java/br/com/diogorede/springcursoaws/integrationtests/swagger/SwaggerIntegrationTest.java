package br.com.diogorede.springcursoaws.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.diogorede.springcursoaws.configs.TestConfigs;
import br.com.diogorede.springcursoaws.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest{
    
    @Test
    public void shouldDisplaySwaggerUiPage(){
        var content = given()
                        .basePath("/swagger-ui/index.html")
                        .port(TestConfigs.SERVER_PORT)
                        .when()
                            .get()
                        .then()
                            .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        System.out.println(content);
        Assertions.assertTrue(content.contains("Swagger UI"));
    }
}