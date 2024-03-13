package br.com.diogorede.springcursoaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.PersonVo;
import br.com.diogorede.springcursoaws.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "People", description = "Endpoints to manage people")
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Find a People", description = "Find a People",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVo.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public PersonVo findById(@PathVariable(value = "id") Long id) throws Exception {
        return personService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds All People", description = "Finds All People",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = PersonVo.class))
                    )
                }
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public List<PersonVo> findAll(){
        return personService.findAll();
    }

    @PostMapping
    @Operation(summary = "Add a People", description = "Add a People, JSON and XML",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public PersonVo create(@RequestBody PersonVo person) {
        return personService.create(person);
    }

    @PutMapping
    @Operation(summary = "Update a People", description = "Update a People, JSON and XML",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public PersonVo update(@RequestBody PersonVo person) {
        return personService.create(person);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Person",
        description = "Delete a Person!",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
        personService.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

}