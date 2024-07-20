package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.model.JwtResponse;
import com.example.demo.jwt.util.JwtTokenUtil;
import com.example.demo.request.LoginRequest;
import com.example.demo.security.service.UserDetailsServiceImpl;

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public LoginController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/auth")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Generazione del token JWT
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtTokenUtil.generateToken(userDetails);

		// Restituzione del token JWT come risposta
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
}
