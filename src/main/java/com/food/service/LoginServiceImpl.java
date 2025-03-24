package com.food.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.LoginException;
import com.food.model.Admin;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.Login;
import com.food.model.LoginDTO;
import com.food.repository.AdminRepo;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private CurrentUserSessionRepo sessionRepo;

	@Override
	public String loginAccount(LoginDTO loginDTO) throws LoginException {
	    if (loginDTO.getRole().equalsIgnoreCase("customer")) {

	        // Validate Customer login
	        Customer customer = customerRepo.findByEmail(loginDTO.getEmail());
	        if (customer == null)
	            throw new LoginException("Invalid email");

	        if (customer.getPassword().equals(loginDTO.getPassword())) {

	            CurrentUserSession currentSession = sessionRepo.findByEmail(loginDTO.getEmail());

	            // Check if user is already logged in
	            if (currentSession != null)
	                throw new LoginException("User already logged-in!");

	            // Create session for Customer
	            currentSession = new CurrentUserSession();
	            currentSession.setEmail(loginDTO.getEmail());
	            currentSession.setLoginDateTime(LocalDateTime.now());
	            currentSession.setRole("customer");
	            currentSession.setCustomerId(customer.getCustomerId());

	            String privateKey = RandomString.make(6); // Generate a random key for session
	            currentSession.setPrivateKey(privateKey);

	            sessionRepo.save(currentSession);
	            return "Login Successful! Your session key: " + privateKey;
	        } else {
	            throw new LoginException("Please enter a valid password");
	        }
	    } else if (loginDTO.getRole().equalsIgnoreCase("admin")) {

	        // Validate Admin login
	        Admin admin = adminRepo.findByEmail(loginDTO.getEmail());
	        if (admin == null)
	            throw new LoginException("Invalid email");

	        if (admin.getPassword().equals(loginDTO.getPassword())) {

	            CurrentUserSession currentSession = sessionRepo.findByEmail(loginDTO.getEmail());

	         
	            if (currentSession != null)
	                throw new LoginException("User already logged-in!");

	            // Create session for Admin
	            currentSession = new CurrentUserSession();
	            currentSession.setEmail(loginDTO.getEmail());
	            currentSession.setLoginDateTime(LocalDateTime.now());
	            currentSession.setRole("admin");

	            String privateKey = RandomString.make(6); // Generate a random key for session
	            currentSession.setPrivateKey(privateKey);

	            sessionRepo.save(currentSession);
	            return "Login Successful! Your session key: " + privateKey;
	        } else {
	            throw new LoginException("Please enter a valid password");
	        }
	    }

	    return null;
	}

	@Override
	public String logoutAccountByEmail(String role, String email) throws LoginException {

	    // Check if email and role are provided
	    if (role == null || role.isEmpty() || email == null || email.isEmpty()) {
	        throw new LoginException("Role and email are required");
	    }

	    // Handle logout for Customer role
	    if (role.equalsIgnoreCase("customer")) {
	        CurrentUserSession currSession = sessionRepo.findByEmail(email);
	        if (currSession == null)
	            throw new LoginException("No session found for this email");

	        if (currSession.getRole().equalsIgnoreCase("customer")) {
	            sessionRepo.delete(currSession);
	            return "Customer logged out successfully!";
	        } else {
	            throw new LoginException("Role mismatch for session");
	        }
	    } 

	    // Handle logout for Admin role
	    else if (role.equalsIgnoreCase("admin")) {
	        CurrentUserSession currSession = sessionRepo.findByEmail(email);
	        if (currSession == null)
	            throw new LoginException("No session found for this email");

	        if (currSession.getRole().equalsIgnoreCase("admin")) {
	            sessionRepo.delete(currSession);
	            return "Admin logged out successfully!";
	        } else {
	            throw new LoginException("Role mismatch for session");
	        }
	    }

	    throw new LoginException("Invalid role");
	}


}
