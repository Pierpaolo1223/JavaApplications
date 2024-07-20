package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.command.UserCommand;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@QueryMapping
	public List<User> findAllUsers() {
		return userService.findAll();
	}

	@QueryMapping
	public User findUserByUsername(@Argument String username) {
		return userService.findByUsername(username);
	}

	@MutationMapping
	public User addUser(@Argument("userCommand") UserCommand userCommand) {
		return userService.addUser(userCommand);
	}

	@MutationMapping
	public boolean deleteUser(@Argument Integer idUser) {
		return userService.deleteUser(idUser);
	}

	@MutationMapping
	public User updateUser(@Argument Integer idUser, @Argument("userCommand") UserCommand userCommand) {
		return userService.updateUser(idUser,userCommand);
	}

}
