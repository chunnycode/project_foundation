package com.sample.shop.login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    @Value("${secret.access}")
    private String secretKey;
    @Value("${secret.refresh}")
    private String refreshKey;

    private final long accessTokenValidTime = 60 * 60 * 1000L; // 1 hour
    private final long refreshTokenValidTime =  7 * 24 * 60 * 60 * 1000L;   // 1 week

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
    }

    public String createAccessToken(String memberId){

        Claims claims = Jwts.claims();  // JWT payload 에 저장되는 정보단위
        claims.put("memberName", memberId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public String createRefreshToken(String memberId){
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshKey)
                .compact();
    }

    public Claims getClaimsFormToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean isValidAccessToken(String token) {
        log.info("isValidToken is : " +token);
        try {
            Claims accessClaims = getClaimsFormToken(token);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access userId: " + accessClaims.get("memberName"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Token Expired memberName : " + exception.getClaims().get("memberName"));
            return false;
        } catch (JwtException exception) {
            log.info("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }

}
