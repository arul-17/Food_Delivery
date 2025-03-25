package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.CategoryException;
import com.food.exception.LoginException;
import com.food.model.Category;
import com.food.model.CurrentUserSession;
import com.food.repository.CurrentUserSessionRepo;
import com.food.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrentUserSessionRepo sessionRepo;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestHeader("Authorization") String authorizationHeader, 
                                                @RequestParam String categoryName) 
                                                throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }
        if (!session.getRole().equals("admin")) {
            throw new LoginException("Admin login required");
        }

        Category newCategory = categoryService.addCategory(key, categoryName);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

   
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestHeader("Authorization") String authorizationHeader, 
                                                   @RequestBody Category category) 
                                                   throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }
        if (!session.getRole().equals("admin")) {
            throw new LoginException("Admin login required");
        }

        Category updatedCategory = categoryService.updateCategory(key, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.ACCEPTED);
    }

  
    @GetMapping("/view/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@RequestHeader("Authorization") String authorizationHeader, 
                                                    @PathVariable Integer categoryId) 
                                                    throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }

        Category category = categoryService.viewCategoryById(key, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


   
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeCategory(@RequestHeader("Authorization") String authorizationHeader, 
                                                 @RequestParam String categoryName) 
                                                 throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }
        if (!session.getRole().equals("admin")) {
            throw new LoginException("Admin login required");
        }

        categoryService.removeCategory(key, categoryName);
        return new ResponseEntity<>("Category removed successfully", HttpStatus.OK);
    }
    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<String> removeCategoryById(@RequestHeader("Authorization") String authorizationHeader, 
                                                 @PathVariable Integer categoryId) 
                                                 throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }
        if (!session.getRole().equals("admin")) {
            throw new LoginException("Admin login required");
        }

        categoryService.removeCategoryById(key, categoryId);
        return new ResponseEntity<>("Category removed successfully", HttpStatus.OK);
    }


   
    @GetMapping("/viewall")
    public ResponseEntity<List<Category>> getAllCategories(@RequestHeader("Authorization") String authorizationHeader) 
                                                           throws CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        CurrentUserSession session = sessionRepo.findByPrivateKey(key);

        if (session == null) {
            throw new LoginException("Login required");
        }

        List<Category> categories = categoryService.viewAllCategory(key);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
