package com.apap.tutorial8.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDB;

@Service

public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDB userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@Override 
	public void updatePassword(UserRoleModel user, String password) {
		String encryptPass = encrypt(password);
		user.setPassword(encryptPass);
		userDb.save(user);
	}
	
	@Override
	public UserRoleModel findByUsername(String username) {
		return userDb.findByUsername(username);
	}
	
	@Override
	public boolean validateOldPassword(UserRoleModel user, String oldPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}
	
	@Override
	public boolean validateNewPassword(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}
	
	@Override
	public boolean validatePassword(String password) {
		Pattern passwordPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z]).{8,}");
		return passwordPattern.equals(password);
	}
	
	
} 
