package com.ashwin.springsecurityjwt.dao;

import javax.sql.DataSource;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ashwin.springsecurityjwt.request.UpdatePasswordRequest;
import com.ashwin.springsecurityjwt.response.ErrorResponse;
import com.ashwin.springsecurityjwt.response.UpdatePasswordResponse;
import com.ashwin.springsecurityjwt.util.MessageConstants;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Object updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		// TODO Auto-generated method stub
		String sql = "UPDATE USER SET PASSWORD=? WHERE ID=? and password=?";
		try {
			int response = jdbcTemplate.update(sql, updatePasswordRequest.getNewPassword(), updatePasswordRequest.getId(), updatePasswordRequest.getOldPassword());
			if(response == 1) {
				UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse();
				updatePasswordResponse.setId(updatePasswordRequest.getId());
				updatePasswordResponse.setResponseMessage(MessageConstants.UPDATESUCCESS);
				return updatePasswordResponse;
			}else {
				ErrorResponse error = new ErrorResponse();
				error.setErrorId(MessageConstants.ERRORID);
				error.setErrorMessage(MessageConstants.UPDATEERROR);
				return error;
			}
		}catch(Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setErrorId(MessageConstants.ERRORID);
			error.setErrorMessage(MessageConstants.UPDATEERROR);
			return error;
			
		}
	}

}
