package com.thiagosol.moviesbattle.dataprovider.gateway;

import com.thiagosol.moviesbattle.core.gateway.AuthGateway;
import com.thiagosol.moviesbattle.entrypoint.config.JwtTokenConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthGatewayImpl implements AuthGateway {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenConfig jwtTokenConfig;

    public AuthGatewayImpl(AuthenticationManager authenticationManager, JwtTokenConfig jwtTokenConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenConfig = jwtTokenConfig;
    }

    public String generateToken(String login, String password){
        var usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(login, password);
        var authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var userDetails = (UserDetails) authenticate.getPrincipal();
        return token(new HashMap<>(), userDetails.getUsername());
    }

    private String token(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenConfig.getExpirationAfter() * 1000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenConfig.getSecret())), SignatureAlgorithm.HS512)
                .compact();
    }
}
