package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LoginResponse;
import com.example.demo.entity.Users;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;


//Commit in iss53
//check conflicts iss53
//check conflict in  main
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user) {
	    LoginResponse response = userService.verify(user);

	    if (response == null) {
	        return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(Map.of("message", "Authentication failed"));
	    }

	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> refreshTokenRequest) {
	    String refreshToken = refreshTokenRequest.get("refreshToken");
	    
	    if (refreshToken == null || !jwtService.validateRefreshToken(refreshToken)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid refresh token"));
	    }

	    String newAccessToken = jwtService.generateToken(jwtService.extractUserName(refreshToken));
	    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}

}
