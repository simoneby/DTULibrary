package com.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.models.*;
import com.repositories.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
// @Author: s191772 and s154666
@SessionAttributes("user")
@Controller
public class LoginController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	private User user;

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

		// Redirects to the Auth DTU service
		// Returns the user with se2-webapp05.compute.dtu.dk/redirect?ticket=[TICKET]
		redirectView.setUrl("https://auth.dtu.dk/dtu/?service=http%3A%2F%2Fse2%2Dwebapp05%2Ecompute%2Edtu%2Edk%3A8080%2Fmazemap%2Fredirect");

		return redirectView;
	}

	// @author s154666
	@GetMapping(value="/redirect")
	public String redirect(@RequestParam("ticket") String ticket, HttpSession httpSession,Model model, HttpServletRequest request) throws MalformedURLException, IOException
	{

		String studentnr = "initial";
		String name = "noname";
		boolean login = false;

		String u = "https://auth.dtu.dk/dtu/servicevalidate?service=http%3A%2F%2Fse2-webapp05%2Ecompute%2Edtu%2Edk%3A8080%2Fmazemap%2Fredirect&ticket=" + ticket;
		if (isUrlValid(u))
		{
			URL url = new URL(u);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
			studentnr = IOUtils.toString(in, "UTF-8").replaceAll("\\s","").replaceAll("\\<.*?\\>", "");

			try 
			{
				User foundUser = null;
				if (userRepository.findUserByStudentnr(studentnr) == null) 
				{
					User entity = new User();
					entity.setEmail(String.format("%s@student.dtu.dk",studentnr));
					entity.setStudentnr(studentnr);
					//entity.addRole(roleRepository.findAll().iterator().next());
					userRepository.save(entity);
					this.user = entity;
					//login = true;

					saveUserInSession(httpSession);
					// GO TO REGISTER PAGE
					return "register";
				}
				else 
				{
					foundUser = userRepository.findUserByStudentnr(studentnr);
					//name = foundUser.getName();
					//login = true;
					this.user = foundUser;
					saveUserInSession(httpSession);
					return "index";
				}
				
			} catch (HibernateException | NullPointerException e){ //POSSIBLY CLEAN UP LATER
				// GO TO REGISTER PAGE
				this.user = new User();
				this.user.setStudentnr(studentnr);

				User entity = new User();
//				entity.setEmail(email);
//				entity.setName("some name");
//				entity.addRole(roleRepository.findAll().iterator().next());
//				userRepository.save(entity);
				saveUserInSession(httpSession);
				return "register";
			}
		}


		return "index";

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

	public void saveUserInSession(HttpSession httpSession) 
	{
		if(httpSession.getAttribute("user")!=null)
			httpSession.removeAttribute("user");
		httpSession.setAttribute("user", this.user);
	}

	@GetMapping(path = "/loginsuccesful")
	public String loginsuccesful(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "loginsuccesful";
	}

	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request, SessionStatus status) {
		status.setComplete();
		return "loginsuccesful";
	}
}
