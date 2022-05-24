package com.sample.shop.shared.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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

    private final long accessTokenValidTime = 60 * 1000L; // 1 hour
    private final long refreshTokenValidTime =  7 * 24 * 60 * 60 * 1000L;   // 1 week

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
    }

    public String createAccessToken(String memberId){

        Claims claims = Jwts.claims();  // JWT payload 에 저장되는 정보단위
        claims.put("memberId", memberId);
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

    public String resolveAccessToken(HttpServletRequest request) {
        log.info("AccessToken is " + request.getHeader("accessToken"));
        return request.getHeader("accessToken");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        log.info("RefreshToken is " + request.getHeader("refreshToken"));
        return request.getHeader("refreshToken");
    }

    public Claims getClaimsAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getClaimsRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(refreshKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidAccessToken(String token) {
        log.info("isValidToken is : " + token);
        try {
            Claims accessClaims = getClaimsAccessToken(token);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access memberId: " + accessClaims.get("memberId"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Access expireTime: " + exception.getClaims().getExpiration());
            log.info("Token Expired memberId : " + exception.getClaims().get("memberId"));
            return false;
        } catch (JwtException exception) {
            log.info("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }

    public boolean isValidRefreshToken(String token){
        log.info("isValidRefreshToken is " + token);

        try {
            Claims accessClaims = getClaimsRefreshToken(token);
            System.out.println("Refresh expireTime: " + accessClaims.getExpiration());
            System.out.println("Refresh memberId: " + accessClaims.get("memberId"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired memberId : " + exception.getClaims().get("memberId"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    /*public boolean isOnlyExpiredAccessToken(String token) {
        Claims accessClaims = getClaimsAccessToken(token).;
        if(!accessClaims.getExpiration().before(new Date())) {
            return true;
        }
        return false;
    }*/

}
