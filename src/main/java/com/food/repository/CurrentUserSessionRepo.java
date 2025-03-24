package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.CurrentUserSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentUserSessionRepo extends JpaRepository<CurrentUserSession, Integer> {
    CurrentUserSession findByPrivateKey(String privateKey);
    CurrentUserSession findByEmail(String email);
    

}
