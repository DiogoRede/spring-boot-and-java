package br.com.diogorede.springcursoaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.security.AccountCredentialsVo;
import br.com.diogorede.springcursoaws.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints to manage auth")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;
    
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and return token")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVo data){

        if(checkParamsIsNotNull(data)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = service.signin(data);
        if(token==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return token;
    }

    public boolean checkParamsIsNotNull(AccountCredentialsVo data){
        return data==null || data.getUsername()==null || data.getUsername().isEmpty()
        || data.getPassword()==null || data.getPassword().isEmpty();
    }

}