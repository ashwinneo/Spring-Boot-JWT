package com.ashwin.springsecurityjwt.services;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

//import org.springframework.security.core.userdetails.User;
import com.ashwin.springsecurityjwt.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashwin.springsecurityjwt.controller.HomeController;
import com.ashwin.springsecurityjwt.models.MyUserDetails;
import com.ashwin.springsecurityjwt.repositories.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
	
	@Autowired
	UserRepository userRespository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		return new User("foo", "foo", new ArrayList<>());
		Optional<User> user = userRespository.findByUserName(username);
		
		user.orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));
		
		return user.map(MyUserDetails::new).get();
	}
	
	public Object registerUser(User user) {
		logger.info("Password: " + user.getPassword());
		user.setRoles("ROLE_USER");
		User userDetails = userRespository.save(user);
		return userDetails;
	}
	

}
