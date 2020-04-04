package com.ashwin.springsecurityjwt.dao;

import org.springframework.stereotype.Repository;

import com.ashwin.springsecurityjwt.request.DeactivateAccountRequest;
import com.ashwin.springsecurityjwt.request.UpdatePasswordRequest;



@Repository
public interface UserDao {

	public Object updatePassword(UpdatePasswordRequest updatePasswordRequest);
	
	public Object deactivateAccount(DeactivateAccountRequest deactivateAccountRequest);
	
	public String getPasswordById(int id);

}
