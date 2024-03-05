package br.com.diogorede.springcursoaws.securityJwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.diogorede.springcursoaws.data.vo.v1.security.TokenVo;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {
    
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-lenght:3600000}")
    private long validityInMilliseconds = 3600000; // 1 hora

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    private TokenVo createAccessToken(String username, List<String> roles){
        Date created = new Date();
        Date expired = new Date(created.getTime() + validityInMilliseconds);

        var accessToken = getAccessToken(username, roles, created, expired);
        var refreshToken = getRefreshToken(username, roles, created);

        return new TokenVo(username, true, created, expired,accessToken, refreshToken);
    }

    private String getAccessToken(String username, List<String> roles, Date created, Date expired) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withSubject(username)
                .withIssuedAt(created)
                .withIssuer(issuerUrl)
                .withExpiresAt(expired)
                .sign(algorithm)
                .strip();
    }

    private String getRefreshToken(String username, List<String> roles, Date created) {
        Date expired = new Date(created.getTime() + (validityInMilliseconds*2));

        return JWT.create()
                .withClaim("roles", roles)
                .withExpiresAt(expired)
                .withSubject(username)
                .withIssuedAt(created)
                .sign(algorithm)
                .strip();
    }

}