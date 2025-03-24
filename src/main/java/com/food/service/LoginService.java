package com.food.service;

import com.food.exception.LoginException;
import com.food.model.*;


public interface LoginService {

	public String loginAccount(LoginDTO loginDTO) throws LoginException;

	
	public String logoutAccountByEmail(String role, String email) throws LoginException;

}
