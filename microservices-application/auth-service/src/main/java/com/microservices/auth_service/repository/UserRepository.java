package com.microservices.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.auth_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	public User findByUsername(String username);
}
