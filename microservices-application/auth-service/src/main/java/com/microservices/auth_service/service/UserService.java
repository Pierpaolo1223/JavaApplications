package com.microservices.auth_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.auth_service.model.User;
import com.microservices.auth_service.repository.UserRepository;

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
}
