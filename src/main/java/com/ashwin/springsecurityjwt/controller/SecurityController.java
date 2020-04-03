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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.springsecurityjwt.request.AuthenticationRequest;
import com.ashwin.springsecurityjwt.request.UpdatePasswordRequest;
import com.ashwin.springsecurityjwt.request.User;
import com.ashwin.springsecurityjwt.response.AuthenticationResponse;
import com.ashwin.springsecurityjwt.response.ErrorResponse;
import com.ashwin.springsecurityjwt.response.Response;
import com.ashwin.springsecurityjwt.services.MyUserDetailService;
import com.ashwin.springsecurityjwt.util.JwtUtil;
import com.ashwin.springsecurityjwt.util.MessageConstants;
@RestController
public class SecurityController {

	Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
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
			if(request.getHeader(MessageConstants.AUTHORIZATION).isEmpty()) {
				logger.info("Empty JWT Token");
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.EMPTYJWTTOKEN);
				return new ResponseEntity<ErrorResponse> (error, HttpStatus.BAD_REQUEST);
			} else {
				logger.info("JWT Token Validating");
				String jwt = request.getHeader(MessageConstants.AUTHORIZATION).substring(7);
				String userName = jwtUtil.extractUsername(jwt);
				if(!userName.equals(authenticationRequest.getUsername())) {
					logger.info("JWT Token not mapped to the user");
					ErrorResponse error = new ErrorResponse();
					error.setErrorId(MessageConstants.ERRORID);
					error.setErrorMessage(MessageConstants.WRONGUSERNAME);
					return new ResponseEntity<ErrorResponse> (error, HttpStatus.BAD_REQUEST);
				}
				
				Response success = new Response();
				success.setId(MessageConstants.SUCCESSID);
				success.setMessage(MessageConstants.JWTTOKENVALIDATED);
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
	
	@PostMapping("/updatePassword")
	public Object updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		logger.info("UpdatePasswordRequest:" + updatePasswordRequest);
		return userDetailService.updatePassword(updatePasswordRequest);
	}
}
