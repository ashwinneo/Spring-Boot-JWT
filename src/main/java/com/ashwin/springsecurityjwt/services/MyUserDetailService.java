package com.ashwin.springsecurityjwt.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import com.ashwin.springsecurityjwt.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashwin.springsecurityjwt.models.MyUserDetails;
import com.ashwin.springsecurityjwt.repositories.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{
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

}
