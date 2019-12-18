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

//@Author s154666, s192671
@RestController
public class RegisterController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping(value = "/signup", headers = "Accept='application/json'")
	public String signup(@SessionAttribute("user") User user, @RequestBody User newUser) {

		if (! userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
			String name2 = newUser.getName();

			User entity = userRepository.findUserByStudentnr(user.getStudentnr());
			entity.setName(name2);

			userRepository.save(entity);

			return "index";
		}
		try {
			String name2 = newUser.getName();
			user.setStudentnr("student nr here");
			user.setName(name2);
			userRepository.save(user);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "index";
		}
	}
}

