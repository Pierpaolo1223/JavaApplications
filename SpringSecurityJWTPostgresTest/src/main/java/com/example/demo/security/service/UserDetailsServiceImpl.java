package com.example.demo.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	public UserService userService;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if(user == null) {
			 throw new UsernameNotFoundException("Could not find user with name '" + username + "'");
		} else {
			 List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
		                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
		                .collect(Collectors.toList());
			 UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
			 return userDetails;
		}
	}

	

}
