package com.taskmanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt-issuer}")
    private String jwtissuer;

    @Value("${jwt.expiry.duration}")
    private long expairytime;

    private Algorithm alogrithm;


    @PostConstruct//run automaticaly run method when project start
    public  void  postConstruct() throws UnsupportedEncodingException {
        alogrithm= Algorithm.HMAC256(algorithmKey);
    }

    //
    public  String generateToken(String userName){
        return JWT.create()
                .withClaim("name",userName)
                .withExpiresAt(new Date(System.currentTimeMillis()+expairytime))
                .withIssuer(jwtissuer)
                .sign(alogrithm);
    }

    public  String getusername(String token){
        DecodedJWT decodejwt = JWT
                .require(alogrithm).
                withIssuer(jwtissuer).
                build().
                verify(token);
        return  decodejwt.getClaim("name").asString();

    }
}
