package br.com.diogorede.springcursoaws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.CategoryVo;
import br.com.diogorede.springcursoaws.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/category/")
@Tag(name = "Category", description = "Endpoints to manage category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    @Operation(
        summary = "Finds all categories",
        description = "Finds all categories",
        tags = {"Category"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryVo.class)))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public List<CategoryVo> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Category", description = "Find a Category",
        tags = {"Category"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = CategoryVo.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public CategoryVo findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Add a Category", description = "Add a Category, JSON and XML",
        tags = {"Category"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = CategoryVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public CategoryVo create(@RequestBody CategoryVo category) {
        return service.create(category);
    }

    @PutMapping
    @Operation(summary = "Update a Category", description = "Update a Category, JSON and XML",
        tags = {"Category"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = CategoryVo.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public CategoryVo update(@RequestBody CategoryVo category){
        return service.update(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Category",
        description = "Delete a Category!",
        tags = {"Category"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id){
        service.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}