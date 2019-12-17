package com.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.models.*;
import com.repositories.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
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

import java.util.HashSet;
import java.util.Set;
import com.models.*;

//@Author s154666
@SessionAttributes("user")
@Component
public class LoginService
{
	@Autowired
	private static FilteredUserRepository userRepository;

	private static User user;

	public static String redirectService(String ticket, HttpSession httpSession,Model model, HttpServletRequest request) 
		throws MalformedURLException, IOException
	{

		String studentnr = "initial";
		String name = "noname";
		boolean login = false;

		String u = "https://auth.dtu.dk/dtu/servicevalidate?service=https%3A%2F%2Fse2-webapp05%2Ecompute%2Edtu%2Edk%3A8443%2Fmazemap%2Fredirect&ticket=" + ticket;
		if (isUrlValid(u))
		{
			URL url = new URL(u);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
			studentnr = IOUtils.toString(in, "UTF-8").replaceAll("\\s","").replaceAll("\\<.*?\\>", "");

			try 
			{
				if (userRepository.findUserByStudentnr(studentnr) == null) 
				{
					registerNewUser(studentnr);
					saveUserInSession(httpSession);
					// GO TO REGISTER PAGE
					return "register";
				}
				else 
				{
					loginExistingUser(studentnr);
					saveUserInSession(httpSession);
					// GO TO INDEX PAGE
					return "index";
				}
				
			} catch (HibernateException | NullPointerException e){ //POSSIBLY CLEAN UP LATER
				// GO TO REGISTER PAGE
				registerNewUser(studentnr);
				saveUserInSession(httpSession);
				return "register";
			}
		}

		// IF URL IS INVALID
		return "index";
	}

	public static User registerNewUser(String studentnr)
	{
		User entity = new User();
		entity.setEmail(String.format("%s@student.dtu.dk",studentnr));
		entity.setStudentnr(studentnr);
		//entity.addRole(roleRepository.findAll().iterator().next());
		userRepository.save(entity);
		return entity;
	}

	public static User loginExistingUser(String studentnr)
	{
		User foundUser = userRepository.findUserByStudentnr(studentnr);
		user = foundUser;
		return foundUser;
	}

	public static void saveUserInSession(HttpSession httpSession) 
	{
		if(httpSession.getAttribute("user")!=null)
			httpSession.removeAttribute("user");
		httpSession.setAttribute("user", user);
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

}