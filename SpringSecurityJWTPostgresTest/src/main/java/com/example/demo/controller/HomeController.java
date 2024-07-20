package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping()
public class HomeController {

	@Autowired
	UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/home")
	public String getCurrentUserId() { // Get della user associata al token creato.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).toString(); // Supponendo che l'ID dell'utente sia rappresentato dal nome
															// utente
		} else {
			return principal.toString(); // Potresti ottenere l'ID in un'altra forma, a seconda della tua configurazione
		}
	}

	@PreAuthorize("hasAuthority('ADMIN') or #username == authentication.name") //o è admin,oppure è l'utente stesso che ha accesso al sistema che vuole sapere le sue credenziali.
	@GetMapping("/home/{username}")
	public String getUserFromDB(@PathVariable String username) { // Get della user associata al token creato.
		return userService.findByUsername(username).getSurname();
	}

}
