package com.lesama;


import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /*@Test
    public void testBuildJwt() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", "lesama");
        dataMap.put("password", "123456");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "bGVzYW1h")
                .addClaims(dataMap)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoibGVzYW1hIiwiZXhwIjoxNzg1MjQwNTEwfQ.Ddzi41Hzgs3wL6bj6li-In-k30jTad1HM7A0vTv4GfI";
        Jwt result = Jwts.parser().setSigningKey("bGVzYW1h").parse(jwt);
        Object body = result.getBody();
        Header header = result.getHeader();
        System.out.println(header);
        System.out.println(body);
    }*/
}
