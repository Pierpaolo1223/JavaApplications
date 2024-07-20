package com.microservices.auth_service.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
	private static final String SECRET_KEY = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629"; // Replace with your actual secret key
	private static final long EXPIRATION_TIME = 600000; 

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities());
		return Jwts.builder().claims(claims).subject(userDetails.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
	}

	public String getUsernameFromToken(String token) {
        /*Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).
        		build().parseUnsecuredClaims(token).getPayload();*/
		Claims claims = (Claims) Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parse(token).getPayload();
        //return claims.getSubject();
		System.out.println(claims.getSubject());
		return claims.getSubject();
    }

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).
    		build().parse(token);
			return true;
		} catch (Exception e) {
			// Token validation failed
			return false;
		}
	}
}
