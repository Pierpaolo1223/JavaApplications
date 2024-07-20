package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.command.UserCommand;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User addUser(UserCommand userCommand) {
		User user = new User();
		user.setPassword(userCommand.getPassword());
		user.setSurname(userCommand.getSurname());
		user.setUsername(userCommand.getUsername());
		return userRepository.save(user);
	}

	public boolean deleteUser(Integer idUser) {
		userRepository.deleteById(Long.valueOf(idUser));
		return true;
	}

	public User updateUser(Integer idUser, UserCommand userCommand) {
		User user = userRepository.findById(Long.valueOf(idUser)).orElseThrow();
		if (userCommand.getUsername() != null) {
			user.setUsername(userCommand.getUsername());
		}
		if (userCommand.getSurname() != null) {
			user.setSurname(userCommand.getSurname());
		}
		if (userCommand.getPassword() != null) {
			user.setPassword(userCommand.getPassword());
		}

		userRepository.save(user); // Salva l'utente aggiornato nel database
		return user;
	}

}
