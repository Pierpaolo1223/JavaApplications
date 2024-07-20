package com.microservices.api_gateway.jwt.util;

import java.util.List;

//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenUtil {
	private static final String SECRET_KEY = "my-secret-key"; // Replace with your actual secret key
	private static final long EXPIRATION_TIME = 600000; 

	/*public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities());
		return Jwts.builder().claims(claims).subject(userDetails.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
	}*/

	public String getUsernameFromToken(String token) {
		Claims claims = (Claims) Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parse(token).getPayload();
        //return claims.getSubject();
		System.out.println(claims.getSubject());
		return claims.getSubject();
    }
	public List<String> getRolesFromToken(String token) {
		Claims claims = (Claims) Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parse(token).getPayload();
		List<String> roles = (List<String>)claims.get("roles",List.class);
		return roles;
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
