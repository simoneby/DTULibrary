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

//@SessionAttributes("user")
@RestController
public class RegisterController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping(value = "/signup", headers = "Accept='application/json'")
	public String signup(@SessionAttribute("user") User user, @RequestParam String name) {
		// return user.toString();

		if (! userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
			

			User entity = userRepository.findUserByStudentnr(user.getStudentnr());
			entity.setName(name);

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

}

