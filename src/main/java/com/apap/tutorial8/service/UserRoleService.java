package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	UserRoleModel findByUsername(String name);
	void updatePassword(UserRoleModel user, String password);
	boolean validateOldPassword(UserRoleModel user, String oldPassword);
	boolean validateNewPassword(String password, String confirmPassword);
	boolean validatePassword(String password);
}
