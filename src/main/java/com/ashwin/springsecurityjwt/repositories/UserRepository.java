package com.ashwin.springsecurityjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashwin.springsecurityjwt.request.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUserName(String userName);
	

}
