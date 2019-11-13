package com.controllers;

import java.util.HashSet;
import java.util.Set;

import com.models.*;
import com.repositories.*;
import com.controllers.UserController;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.*;
import java.lang.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.net.URLConnection;
import java.net.*;
import java.util.Scanner;
import java.io.IOException;
import org.springframework.*;

// import com.controllers.RedirectController.*;
import org.springframework.web.servlet.view.RedirectView;

@SessionAttributes("user")
@Controller
public class LoginController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	private User user;
	private UserController userController;

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
//	@RequestMapping(value = "/signin", method = RequestMethod.POST)
//	public String signin(
//			@RequestParam(value = "email", required = true, defaultValue = "") String email,
//			@RequestParam(value = "password", required = false, defaultValue = "") String password) {
//		/*
//		 * if(roleRepository.findAll()==null ||
//		 * !roleRepository.findAll().iterator().hasNext()) { roleRepository.save(new
//		 * Role("Student")); roleRepository.save(new Role("Faculty"));
//		 * roleRepository.save(new Role("Library Staff")); }
//		 */
//
//		// check DTU inside sign in
//		if (email == null)
//			return "Email is null";
//		boolean succesfulDTUSignIn = true;
//		boolean login = false;
//		if (succesfulDTUSignIn) {
//			if (userRepository.findUsersByEmail(email).isEmpty()) {
//				User entity = new User();
//				entity.setEmail(email);
//				entity.setName("some name");
//				entity.addRole(roleRepository.findAll().iterator().next());
//				userRepository.save(entity);
//				this.user = entity;
//				login = true;
//			} else {
//				this.user= userRepository.findUserByEmail(email);
//				login = true;
//			}
//		} else {
//			if (!userRepository.findUsersByEmail(email).isEmpty()) {
//				this.user = userRepository.findUserByEmail(email);
//				login = true;
//			}
//		}
//		if(login)
//		{
//			setUpUserForm();
//			return loginsuccesful(this.user.getEmail());
//		}
//		return "Login Failed!";
//	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public RedirectView login(HttpServletResponse HttpServletResponse)
	{
		RedirectView redirectView = new RedirectView();

		// FOR REALSIES
		redirectView.setUrl("https://auth.dtu.dk/dtu/?service=http%3A%2F%2Fse2%2Dwebapp05%2Ecompute%2Edtu%2Edk%3A8080%2Fmazemap%2Fredirect");



		return redirectView;
	}

	// @author s154666
	@RequestMapping(value="/redirect", method=RequestMethod.GET)
	@ResponseBody
	public String redirect(@RequestParam("ticket") String ticket) throws MalformedURLException, IOException
	{

		String studentnr = "no";
		String name = "noname";


		String u = "https://auth.dtu.dk/dtu/servicevalidate?service=http%3A%2F%2Fse2-webapp05%2Ecompute%2Edtu%2Edk%3A8080%2Fmazemap%2Fredirect&ticket=" + ticket;
		if (isUrlValid(u))
		{
			URL url = new URL(u);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
			encoding = encoding == null ? "UTF-8" : encoding;
			studentnr = IOUtils.toString(in, encoding);

			try 
			{

				User foundUser = userController.getUserByStudentnr(studentnr);
				name = foundUser.getName();
			} catch (HibernateException | NullPointerException e){}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://se2-webapp05.compute.dtu.dk:8080/mazemap/register");

		//return redirectView;

		return "." + studentnr + ".";

	}

	public static boolean isUrlValid(String url) {
		try {
			URL obj = new URL(url);
			obj.toURI();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}





	//{

		// USER WILL RETURN WITH URL
		// https://se2-webapp05.compute.dtu.dk:8080?ticket=[TICKET]
		// WHERE TICKET IS USED TO GET DATA FROM CAMPUSNET ( I THINK ??? )

		// ON SUCCESSFUL LOGIN:
		// DTU CAS RESPONDS WITH
		// yes
		// [USERNAME] (NOT THE USER'S NAME BUT THE USERNAME IN CAS)


		// ON UNSUCCESSFUL LOGIN:
		// DTU CAS RESPONDS WITH
		// no



	//	return "Grats you've been logged in!\t";
	//}





	@GetMapping(path="/loginsuccesful")
    public String loginsuccesful(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "loginsuccesful";
    }
}