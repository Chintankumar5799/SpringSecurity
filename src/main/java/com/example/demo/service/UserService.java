package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repo.UserRepo;


@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}


public com.example.demo.entity.LoginResponse verify(Users user) {
    try {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Generate JWT tokens
            String accessToken = jwtService.generateToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            // Return both access token and refresh token
            return new com.example.demo.entity.LoginResponse(accessToken, refreshToken);
        }
    } catch (BadCredentialsException e) {
        return null; // or a more structured error response
    }

    return null;  // or throw an exception for better handling
}



//	public String verify(Users user) {
//		try {
//		Authentication authentication=authManager.authenticate(
//				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));;
//		
//		if(authentication.isAuthenticated()) {
//			return jwtService.generateToken(user.getUsername());
//		}
//		}catch(BadCredentialsException e) {
//			return "Fail";
//		}
//		return "Fail";
//	}
}
