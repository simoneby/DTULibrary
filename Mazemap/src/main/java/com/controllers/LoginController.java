package com.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes("user")
@Controller
public class LoginController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;


	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signin(HttpSession httpSession,Model model, HttpServletRequest request, @RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = false, defaultValue = "") String password) {
				User user = null;
		/*
		 * if(roleRepository.findAll()==null ||
		 * !roleRepository.findAll().iterator().hasNext()) { roleRepository.save(new
		 * Role("Student")); roleRepository.save(new Role("Faculty"));
		 * roleRepository.save(new Role("Library Staff")); }
		 */

		// check DTU inside sign in
		if (email == null)
			return "Email is null";
		boolean succesfulDTUSignIn = true;
		boolean login = false;
		if (succesfulDTUSignIn) {
			if (userRepository.findUsersByEmail(email).isEmpty()) {
				User entity = new User();
				entity.setEmail(email);
				entity.setName("some name");
				entity.addRole(roleRepository.findAll().iterator().next());
				userRepository.save(entity);
				user = entity;
				login = true;
			} else {
				user = userRepository.findUserByEmail(email);
				login = true;
			}
		} else {
			if (!userRepository.findUsersByEmail(email).isEmpty()) {
				user = userRepository.findUserByEmail(email);
				login = true;
			}
		}
		//loggedInUser = setUpUserForm();
		if (login) {
			if(httpSession.getAttribute("user")!=null)
				httpSession.removeAttribute("user");
			httpSession.setAttribute("user", user);
			return "loginsuccesful";
		}
		return "Login Failed!";
	}

	@GetMapping(path = "/loginsuccesful")
	public String loginsuccesful(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "loginsuccesful";
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request, SessionStatus status) {
		status.setComplete();
		return "loginsuccesful";
	}
}