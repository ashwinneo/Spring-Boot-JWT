 package com.ashwin.springsecurityjwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.springsecurityjwt.filters.JwtRequestFilter;
import com.ashwin.springsecurityjwt.request.AuthenticationRequest;
import com.ashwin.springsecurityjwt.request.User;
import com.ashwin.springsecurityjwt.response.AuthenticationResponse;
import com.ashwin.springsecurityjwt.response.Response;
import com.ashwin.springsecurityjwt.services.MyUserDetailService;
import com.ashwin.springsecurityjwt.util.JwtUtil;

@RestController
public class SecurityController {

	Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	private static final String AUTHORIZATION = "Authorization";
	private static final String BADREQUESTS = "Enter JWT Token";
	private static final String ERRORID = "1000";
	private static final String SUCCESSID = "200";
	private static final String ERRORMESSAGE = "Empty JWT token";
	private static final String SUCCESSMESSAGE = "JWT token validated";
	
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
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, @RequestParam("reauthenticate") boolean reauthenticate, HttpServletRequest request) throws Exception {
		if(reauthenticate == false) {
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
		}else {
			if(request.getHeader(AUTHORIZATION).isEmpty()) {
				logger.info("Empty JWT Token");
				Response error = new Response();
				error.setId(ERRORID);
				error.setMessage(ERRORMESSAGE);
				error.setJwt(null);
				return new ResponseEntity<Response> (error, HttpStatus.BAD_REQUEST);
			} else {
				String jwt = request.getHeader(AUTHORIZATION).substring(7);
				Response success = new Response();
				success.setId(SUCCESSID);
				success.setMessage(SUCCESSMESSAGE);
				success.setJwt(jwt);
				logger.info("Success: JWT Validated");
				return new ResponseEntity<Response> (success, HttpStatus.OK);
			}
		}
	}
	
	@PostMapping("/register")
	public Object register(@RequestBody User user) {
		logger.info("User : " + user);
		return userDetailService.registerUser(user);
	}
}
