package com.ashwin.springsecurityjwt.services;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashwin.springsecurityjwt.controller.SecurityController;
import com.ashwin.springsecurityjwt.repositories.UserRepository;
import com.ashwin.springsecurityjwt.request.MyUserDetails;
import com.ashwin.springsecurityjwt.request.User;
import com.ashwin.springsecurityjwt.response.Response;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
	private String errorId = "";
	private String errorMessage = "";
	
	@Autowired
	UserRepository userRespository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		return new User("foo", "foo", new ArrayList<>());
		logger.info("Authorizing user from user table");
		Optional<User> user = userRespository.findByUserName(username);
		logger.info("User object fetched " + user);
		user.orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));
		
		return user.map(MyUserDetails::new).get();
	}
	
	public Object registerUser(User user) {
		
		boolean isError = validateInput(user);
		
		if(!isError) {
			logger.info("Registering user Start: " + user.getUserName() + user.getPassword());
			user.setRoles("ROLE_USER");
			logger.info("Inserting user data to database");
			User userDetails;
			try {
				userDetails = userRespository.save(user);
				logger.info("Inserting user data done");
				return userDetails;
			}catch(Exception e) {
				logger.info("Error inserting user data " + e);
				Response response = new Response();
				response.setId("1001");
				response.setMessage("Error inserting user data " + e);
				return response;
			}
		} else {
			logger.info("Invalid user data");
			Response response = new Response();
			response.setId(errorId);
			response.setMessage(errorMessage);
			return response;
		}
		
		
	}
	
	private boolean validateInput(User user) {
		boolean isError = false;
		
		if(user.getUserName().isEmpty() || user.getUserName().equals("null")) {
			isError = true;
			logger.info("Username is empty");
			errorId = "1001";
			errorMessage = "Username is empty";
		} else if(user.getPassword().isEmpty() || user.getPassword().equals("null")) {
			isError = true;
			logger.info("Password is empty");
			errorId = "1001";
			errorMessage = "Password is empty";
		} else if(user.isActive() == false) {
			isError = true;
			logger.info("User is inactive");
			errorId = "1001";
			errorMessage = "User is inactive";
		}
		return isError;
	}
	

}
