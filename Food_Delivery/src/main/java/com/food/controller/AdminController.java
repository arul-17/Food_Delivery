package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.exception.AdminException;
import com.food.model.Admin;
import com.food.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/new")
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
	    try {
	        System.out.println("Received request to create admin: " + admin); // Debugging log
	        String result = adminService.createNewAdmin(admin);
	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	    } catch (AdminException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}
	

}