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
import org.springframework.util.ObjectUtils;

import com.ashwin.springsecurityjwt.controller.SecurityController;
import com.ashwin.springsecurityjwt.dao.UserDao;
import com.ashwin.springsecurityjwt.repositories.UserRepository;
import com.ashwin.springsecurityjwt.request.DeactivateAccountRequest;
import com.ashwin.springsecurityjwt.request.MyUserDetails;
import com.ashwin.springsecurityjwt.request.UpdatePasswordRequest;
import com.ashwin.springsecurityjwt.request.User;
import com.ashwin.springsecurityjwt.response.DeactivateAccountResponse;
import com.ashwin.springsecurityjwt.response.ErrorResponse;
import com.ashwin.springsecurityjwt.response.Response;
import com.ashwin.springsecurityjwt.response.UpdatePasswordResponse;
import com.ashwin.springsecurityjwt.util.MessageConstants;
@Service
public class MyUserDetailService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);
	private String errorId = "";
	private String errorMessage = "";
	
	@Autowired
	UserRepository userRespository;

	@Autowired
	UserDao userDao;
	
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
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setErrorId(MessageConstants.ERRORID);
				errorResponse.setErrorMessage(MessageConstants.INSERTERROR);
				return errorResponse;
			}
		} else {
			logger.info("Validation failed " + errorMessage);
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorId(errorId);
			errorResponse.setErrorMessage(errorMessage);
			return errorResponse;
		}
		
		
	}
	
	private boolean validateInput(User user) {
		boolean isError = false;
		logger.info("Validating User input for register" +user);
		if(user.getUserName().isEmpty() || user.getUserName().equals("null")) {
			isError = true;
			logger.info("Username is empty");
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYUSERNAME;
		} else if(user.getPassword().isEmpty() || user.getPassword().equals("null")) {
			isError = true;
			logger.info("Password is empty");
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYPASSWORD;
		} else if(user.isActive() == false) {
			isError = true;
			logger.info("User is inactive");
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.INACTIVEUSER;
		}
		return isError;
	}

	public Object updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		// TODO Auto-generated method stub
		logger.info("Update password PUT :" + updatePasswordRequest);
		boolean isError = validateUpdatePasswordInuput(updatePasswordRequest);
		if(!isError) {
			try {
				return userDao.updatePassword(updatePasswordRequest);
				
			}catch(Exception e) {
				logger.info("Error updating password PUT :" + e);
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.UPDATEERROR);
				return error;
			}
		} else {
			logger.info("Update password validation failed " + errorMessage);
			ErrorResponse error = new ErrorResponse();
			error.setErrorId(errorId);
			error.setErrorMessage(errorMessage);
			return error;
		}
	}
	
	private boolean validateUpdatePasswordInuput(UpdatePasswordRequest updatePasswordRequest) {
		boolean isError = false;
		logger.info("Validating UserpasswordRequest " +updatePasswordRequest);
		if(ObjectUtils.isEmpty(updatePasswordRequest.getId())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYUSERID;
		} 
		else if(ObjectUtils.isEmpty(updatePasswordRequest.getOldPassword())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYOLDPASSWORD;
		} else if(ObjectUtils.isEmpty(updatePasswordRequest.getNewPassword())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYNEWPASSWORD;
		}
		return isError;
	}

	public Object deactivateAccount(DeactivateAccountRequest deactivateAccountRequest) {
		// TODO Auto-generated method stub
		logger.info("Deactivate Account PUT: " + deactivateAccountRequest);
		boolean isError = validateDeactivateAccountPassword(deactivateAccountRequest);
		Object response = null;
		if(!isError) {
			try {
				String password = userDao.getPasswordById(deactivateAccountRequest.getId());
				logger.info("Deactivate Account password: " + password);
				if(password != null && password.equals(deactivateAccountRequest.getPassword())) {
					return response = userDao.deactivateAccount(deactivateAccountRequest);
				} else {
					logger.info("Deactivate Account: Password not same");
					ErrorResponse error = new ErrorResponse();
					error.setErrorId(MessageConstants.ERRORID);
					error.setErrorMessage(MessageConstants.PASSWORDNOTSAME);
					return error;
				}
			}catch(Exception e) {
				logger.info("Deactivate Account Exception : " + e);
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.DEACTIVATEACCOUNTFAIL + e);
				return error;
			}
		} else {
			logger.info("Deactivate account validation failed");
			ErrorResponse error = new ErrorResponse();
			error.setErrorId(errorId);
			error.setErrorMessage(errorMessage);
			return error;
		}
	}
	
	private boolean validateDeactivateAccountPassword(DeactivateAccountRequest deactivateAccountRequest) {
		boolean isError = false;
		
		if(ObjectUtils.isEmpty(deactivateAccountRequest.getId())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYUSERID;
		} else if(ObjectUtils.isEmpty(deactivateAccountRequest.getPassword())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYPASSWORD;
		} else if(ObjectUtils.isEmpty(deactivateAccountRequest.isActive())) {
			isError = true;
			errorId = MessageConstants.EMPTYERRORID;
			errorMessage = MessageConstants.EMPTYACTIVESTATUS;
		}
		
		return isError;
	}
	

}
