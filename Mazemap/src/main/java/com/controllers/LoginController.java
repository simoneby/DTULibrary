package com.controllers;
import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import java.util.*;
@RestController
public class LoginController 
{
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam(value="studnum", required=false, defaultValue="") String name) 
	{

		return "Grats you've been logged in!\t" + name;
	}

	@RequestMapping(value="/loginNew",method=RequestMethod.POST)
	public String loginWithEmail(@RequestParam(value="email", required=true, defaultValue="") String email,@RequestParam(value="password", required=false, defaultValue="") String password) 
	{
		/*
		 * if(roleRepository.findAll()==null ||
		 * !roleRepository.findAll().iterator().hasNext()) { roleRepository.save(new
		 * Role("Student")); roleRepository.save(new Role("Faculty"));
		 * roleRepository.save(new Role("Library Staff")); }
		 */
		
		//check DTU inside sign in 
		boolean succesfulSignIn = true;
		if(succesfulSignIn)
		{
		if(userRepository.findUsersByEmail(email).isEmpty())
		{
			User entity = new User();
			entity.setEmail(email);
			entity.setName("some name");
			entity.addRole(roleRepository.findAll().iterator().next());
			userRepository.save(entity);
			return "User added";
		}
		else
		{
			return "Login succesful";
		}
		}else
		{
			if(!userRepository.findUsersByEmail(email).isEmpty())
			{
				return "Login succesfull :)";
			}
			return "Credentials not recognized";
		}

	}
}