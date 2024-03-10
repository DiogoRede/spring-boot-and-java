package br.com.diogorede.springcursoaws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.diogorede.springcursoaws.data.vo.v1.security.AccountCredentialsVo;
import br.com.diogorede.springcursoaws.data.vo.v1.security.TokenVo;
import br.com.diogorede.springcursoaws.repositories.UserRepository;
import br.com.diogorede.springcursoaws.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVo vo){
        try {
            String username = vo.getUsername();
            String password = vo.getPassword();

            manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVo();

            if(user!=null){
                tokenResponse = provider.createAccessToken(username, user.getRoles());
            }else{
               throw new UsernameNotFoundException("Username " + username+ " not found!");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken){

        var user = repository.findByUsername(username);

        var tokenResponse = new TokenVo();

        if(user!=null){
            tokenResponse = provider.refreshToken(refreshToken);
        }else{
            throw new UsernameNotFoundException("Username " + username+ " not found!");
        }

        return ResponseEntity.ok(tokenResponse);
    }

}