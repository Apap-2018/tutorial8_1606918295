package com.apap.tutorial8.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, RedirectAttributes redirect) {
		if (userService.validatePassword(user.getPassword())) {
			userService.addUser(user);
			return "home";
		}
		redirect.addFlashAttribute("alert", "alert-red");
		redirect.addFlashAttribute("alertText", "Error! Your password must contains at least 8 character and number");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update-password", method = RequestMethod.GET)
	private String updatePassword() {
		return "update-password";
	}
	
	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	private String updatePasswordSubmit(Locale locale,
			@RequestParam("password") String password,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirect) {
		UserRoleModel user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (userService.validateOldPassword(user, oldPassword)) {
			if (userService.validateNewPassword(password, confirmPassword)) {
				if (userService.validatePassword(password)) {
					userService.updatePassword(user, password);
					return "redirect:/";
				}
				redirect.addFlashAttribute("alert", "alert-red");
				redirect.addFlashAttribute("alertText", "Error! Your password must contains at least 8 character and number");
				return "redirect:/user/update-password";	
			}
			redirect.addFlashAttribute("alert", "alert-red");
			redirect.addFlashAttribute("alertText", "Error! Your passwords didn't match");
			return "redirect:/user/update-password";
		}
		redirect.addFlashAttribute("alert", "alert-red");
		redirect.addFlashAttribute("alertText", "Error! Invalid password!");
		return "redirect:/user/update-password";
	}
	
	 
}
