package com.mungtamjung.petective.security;

import com.mungtamjung.petective.model.JwtProperties;
import com.mungtamjung.petective.model.UserEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    //token 생성
    public String createToken(UserEntity userEntity){
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //header type: jwt
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setSubject(String.valueOf(userEntity.getId()))
                .claim("id", userEntity.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //token 유효성 검사
    public String validateTokenAndGetUser(String token){
        //try{
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) //복호화
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        //} catch (Exception e){
        //    return e.getMessage(); //에러 발생
        //}
    }
}
