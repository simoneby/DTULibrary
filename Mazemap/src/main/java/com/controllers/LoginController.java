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

@SessionAttributes("user")
@Controller
public class LoginController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	private User user;

	@ModelAttribute("user")
	public User setUpUserForm() {
	return user;
	}

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public String login(@RequestParam(value = "studnum", required = false,
	// defaultValue = "") String name) {

	// return "Grats you've been logged in!\t" + name;
	// }

	// @RequestMapping(method = RequestMethod.POST,value = "/loginNew",headers =
	// "Accept='application/json'")
	// public String loginWithEmail(@ModelAttribute("user") User user) {
	// /*
	// * if(roleRepository.findAll()==null ||
	// * !roleRepository.findAll().iterator().hasNext()) { roleRepository.save(new
	// * Role("Student")); roleRepository.save(new Role("Faculty"));
	// * roleRepository.save(new Role("Library Staff")); }
	// */
	// if(user == null || user.getEmail() == null)
	// return "not logged in";

	// // check DTU inside sign in
	// boolean succesfulSignIn = true;

	// if (succesfulSignIn) {
	// if (userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
	// User entity = new User();
	// entity.setEmail(user.getEmail());
	// entity.setName("some name");
	// entity.addRole(roleRepository.findAll().iterator().next());
	// userRepository.save(entity);
	// user = entity;
	// //return entity;
	// return "User added";
	// } else {
	// //return userRepository.findUserByEmail(user.getEmail());
	// return String.format("Login successful %s",user.getEmail());
	// }
	// } else {
	// if (!userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
	// //return userRepository.findUserByEmail(user.getEmail());
	// //return user;
	// return "Login succesfull :)";
	// }
	// //return String;
	// return "Credentials not recognized";
	// }
	// }
	//@ModelAttribute("user")
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signin(
			@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = false, defaultValue = "") String password) {
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
				this.user = entity;
				login = true;
			} else {
				this.user= userRepository.findUserByEmail(email);
				login = true;
			}
		} else {
			if (!userRepository.findUsersByEmail(email).isEmpty()) {
				this.user = userRepository.findUserByEmail(email);
				login = true;
			}
		}
		if(login)
		{
			setUpUserForm();
			return loginsuccesful(this.user.getEmail());
		}
		return "Login Failed!";
	}
	@GetMapping(path="/loginsuccesful")
    public String loginsuccesful(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "loginsuccesful";
    }
}