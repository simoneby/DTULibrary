package com.controllers;
import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

//@Author s192671
@RestController
@RequestMapping("roles")
public class RoleController {
	@Autowired
	RoleRepository roleRepository;

	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ArrayList<Role> getAll() 
	{
		ArrayList<Role> roles = new ArrayList<Role>();
		roleRepository.findAll().forEach(roles::add);
		return roles;
	}
	
}
