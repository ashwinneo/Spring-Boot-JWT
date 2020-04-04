package com.ashwin.springsecurityjwt.dao;

import javax.sql.DataSource;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ashwin.springsecurityjwt.request.DeactivateAccountRequest;
import com.ashwin.springsecurityjwt.request.UpdatePasswordRequest;
import com.ashwin.springsecurityjwt.response.DeactivateAccountResponse;
import com.ashwin.springsecurityjwt.response.ErrorResponse;
import com.ashwin.springsecurityjwt.response.UpdatePasswordResponse;
import com.ashwin.springsecurityjwt.services.MyUserDetailService;
import com.ashwin.springsecurityjwt.util.MessageConstants;

@Repository
public class UserDaoImpl implements UserDao{
	
	Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Object updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		// TODO Auto-generated method stub
		logger.info("DAOImpl: executing for update password");
		String sql = "UPDATE USER SET PASSWORD=? WHERE ID=? and password=?";
		try {
			logger.info("DAOImpl: Inside try block " + sql);
			int response = jdbcTemplate.update(sql, updatePasswordRequest.getNewPassword(), updatePasswordRequest.getId(), updatePasswordRequest.getOldPassword());
			logger.info("DAOImpl: " + response);
			if(response == 1) {
				logger.info("DAOImpl: Update password success");
				UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse();
				updatePasswordResponse.setId(updatePasswordRequest.getId());
				updatePasswordResponse.setResponseMessage(MessageConstants.UPDATESUCCESS);
				return updatePasswordResponse;
			}else {
				logger.info("DAOImpl: Update password fail");
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.UPDATEERROR);
				return error;
			}
		}catch(Exception e) {
			logger.info("DAOImpl: Update password fail" + e);
			ErrorResponse error = new ErrorResponse();
			error.setErrorId(MessageConstants.ERRORID);
			error.setErrorMessage(MessageConstants.UPDATEERROR);
			return error;
			
		}
	}

	@Override
	public Object deactivateAccount(DeactivateAccountRequest deactivateAccountRequest) {
		// TODO Auto-generated method stub
		String query = "UPDATE USER SET ACTIVE=? WHERE ID=?";
		try {
			logger.info("DAOImpl: Deactivate account: Inside try block");
			int response = jdbcTemplate.update(query, deactivateAccountRequest.isActive(), deactivateAccountRequest.getId());
			if(response == 1) {
				logger.info("DAOImpl: Deactivate account success");
				DeactivateAccountResponse deactivateAccountResponse = new DeactivateAccountResponse();
				deactivateAccountResponse.setId(deactivateAccountRequest.getId());
				deactivateAccountResponse.setActive(deactivateAccountRequest.isActive());
				deactivateAccountResponse.setResponseStatus(MessageConstants.DEACTIVATEACCOUNT);
				return deactivateAccountResponse;
			} else {
				logger.info("DAOImpl: Deactivate account fail");
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.DEACTIVATEACCOUNTFAIL);
				return error;
			}

		}catch(Exception e) {
			logger.info("DAOImpl: Deactivate account Exception " + e);
			ErrorResponse error = new ErrorResponse();
			error.setErrorId(MessageConstants.ERRORID);
			error.setErrorMessage(MessageConstants.DEACTIVATEACCOUNTFAIL);
			return error;
		}
	}

	@Override
	public String getPasswordById(int id) {
		// TODO Auto-generated method stub
		try {
			String query = "SELECT password from user where id=?";
			return jdbcTemplate.queryForObject(query, new Object[] {id}, String.class);
		}catch(Exception e) {
			return "NULL";
		}
	}

}
