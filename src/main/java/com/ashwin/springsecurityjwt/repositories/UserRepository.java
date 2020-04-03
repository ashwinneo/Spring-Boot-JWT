package com.ashwin.springsecurityjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ashwin.springsecurityjwt.request.User;
import com.ashwin.springsecurityjwt.response.UpdatePasswordResponse;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUserName(String userName);
	
	
}
