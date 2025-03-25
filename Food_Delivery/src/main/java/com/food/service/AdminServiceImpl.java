package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food.exception.AdminException;
import com.food.model.Admin;
import com.food.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createNewAdmin(Admin admin) throws AdminException {
    	 if (admin.getPassword().length() < 6 || admin.getPassword().length() > 16) {
    	        throw new AdminException("Password should be between 6 to 16 characters.");
    	    }
       
        Admin existsAdmin = adminRepo.findByEmail(admin.getEmail());
        if (existsAdmin != null) {
            throw new AdminException("Admin already exists with email: " + admin.getEmail());
        }

       //Hash the password before saving
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepo.save(admin);

        return "Admin created successfully with email: " + admin.getEmail();
    }
}
