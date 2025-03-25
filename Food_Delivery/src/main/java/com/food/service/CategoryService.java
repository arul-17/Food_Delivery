package com.food.service;

import java.util.List;

import com.food.exception.CategoryException;
import com.food.exception.LoginException;
import com.food.model.Category;

public interface CategoryService {

	public Category addCategory(String key, String categoryName) throws CategoryException, LoginException;

	public Category removeCategory(String key, String categoryName) throws CategoryException, LoginException;

	public List<Category> viewAllCategory(String key) throws CategoryException, LoginException;

	public Category viewCategoryById(String key, Integer categoryId) throws CategoryException, LoginException;

	public Category updateCategory(String key, Category category) throws CategoryException, LoginException;

	public void removeCategoryById(String key, Integer categoryId) throws CategoryException, LoginException;

}
