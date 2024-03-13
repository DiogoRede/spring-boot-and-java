package br.com.diogorede.springcursoaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.MovieVo;
import br.com.diogorede.springcursoaws.services.MovieService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/movie")
@Tag(name = "Movie", description = "Endpoints to manage movie")
public class MovieController {
    
    @Autowired
    private MovieService service;

    @GetMapping("/{id}")
    @Operation(summary = "Find a Movie", description = "Find a Movie",
        tags = {"Movie"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = MovieVo.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public MovieVo findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds All movies", description = "Finds All movies",
        tags = {"Movie"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = MovieVo.class))
                    )
                }
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public List<MovieVo> findAll(){
        return service.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a movie", description = "Create a movie, JSON and XML",
        tags = {"Movie"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = MovieVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public MovieVo create(@RequestBody MovieVo movie) {        
        return service.create(movie);
    }

    @PutMapping
    @Operation(summary = "Updated a movie", description = "Updated a movie",
        tags = {"Movie"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = MovieVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public MovieVo update(@RequestBody MovieVo movie) {
        return service.update(movie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie", description = "Delete a movie",
        tags = {"Movie"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}