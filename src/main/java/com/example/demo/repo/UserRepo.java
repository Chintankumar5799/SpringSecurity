package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);



}
