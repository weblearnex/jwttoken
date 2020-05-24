package com.authValidation.jwttoken.service.inf;

import com.authValidation.jwttoken.model.entity.User;

public interface JwtTokenServiceInf {

	String login(String name, String password);

	User save(User user);

	boolean logout(String tokenString);

	Boolean checkTokenIsValid(String tokenString);

	String createToken(String tokenString);

	User getUser(String username);
	
	User findById(Long id);

}
