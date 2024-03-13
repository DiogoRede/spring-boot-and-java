package br.com.diogorede.springcursoaws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diogorede.springcursoaws.data.vo.v1.security.AccountCredentialsVo;
import br.com.diogorede.springcursoaws.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated a user and return token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable String username, 
                                       @RequestHeader("Authorization") String refreshToken){

        if(checkParamsIsNotNull(username, refreshToken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        var token = service.refreshToken(username, refreshToken);
        if(token==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return token;
    }

    public boolean checkParamsIsNotNull(AccountCredentialsVo data){
        System.out.println(data.getUsername());
        System.out.println(data.getPassword());

        return data==null || data.getUsername()==null || data.getUsername().isBlank()
        || data.getPassword()==null || data.getPassword().isBlank();
    }

    public boolean checkParamsIsNotNull(String username, String refreshToken){
        return username==null || username.isBlank() || 
            refreshToken==null || refreshToken.isBlank();
    }

}