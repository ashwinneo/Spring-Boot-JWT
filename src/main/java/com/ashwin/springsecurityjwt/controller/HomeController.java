package com.ashwin.springsecurityjwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.springsecurityjwt.filters.JwtRequestFilter;
import com.ashwin.springsecurityjwt.models.User;
import com.ashwin.springsecurityjwt.request.AuthenticationRequest;
import com.ashwin.springsecurityjwt.response.AuthenticationResponse;
import com.ashwin.springsecurityjwt.services.MyUserDetailService;
import com.ashwin.springsecurityjwt.util.JwtUtil;

@RestController
public class HomeController {

	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping("/hello")
	public String hello() {
		logger.info("HELLO");
		return "HELLO";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			logger.info("Into Authenticate API: ");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PostMapping("/register")
	public Object register(@RequestBody User user) {
		logger.info("User : " + user);
		
		return userDetailService.registerUser(user);
	}
}
