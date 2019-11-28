package com.controllers;

import java.util.HashSet;
import java.util.Set;

import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@SessionAttributes("user")
@RestController
public class RegisterController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	private User user;

	@GetMapping(value = "/signup")
	public String signup(@SessionAttribute("user") User user, @RequestParam String name, @RequestParam String studentnr, HttpSession httpSession) {
		// return user.toString();

		if (! userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
			


			User entity = new User();
			entity.setEmail(String.format("%s@student.dtu.dk",studentnr));
			entity.setStudentnr(studentnr);
			entity.setName(name);
			userRepository.save(entity);
			this.user = entity;

			saveUserInSession(httpSession);
			// GO TO REGISTER PAGE

			userRepository.save(entity);

			return String.format("A user with the email %s already exists!", user.getEmail());
		}
		try {
			user.setStudentnr("student nr here");
			user.setName(name);
			userRepository.save(user);
			return "You have been signed up!";
		} catch (Exception e) {
			e.printStackTrace();
			return "There was an error! User was not created! Please Try again! " + user.toString();
		}
	}

	public void saveUserInSession(HttpSession httpSession)
	{
		if(httpSession.getAttribute("user")!=null)
			httpSession.removeAttribute("user");
		httpSession.setAttribute("user", this.user);
	}

}

