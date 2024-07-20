package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
