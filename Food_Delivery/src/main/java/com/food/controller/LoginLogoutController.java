package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.exception.LoginException;
import com.food.model.LoginDTO;
import com.food.service.LoginService;

@RestController
@RequestMapping("/app")
public class LoginLogoutController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<String> logIn(@RequestBody LoginDTO loginDTO) throws LoginException {
		String result = loginService.loginAccount(loginDTO);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String role, @RequestParam String email) throws LoginException {
      
        if (role == null || role.isEmpty() || email == null || email.isEmpty()) {
            return new ResponseEntity<>("Role and email are required", HttpStatus.BAD_REQUEST);
        }

      
        String result = loginService.logoutAccountByEmail(role, email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
