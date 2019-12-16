package com.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.models.*;
import com.repositories.*;
import com.services.*;
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

import org.springframework.web.servlet.view.RedirectView;
// @Author: s191772, s154666
@SessionAttributes("user")
@Controller
public class LoginController {
	@Autowired
	private FilteredUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	private User user;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public RedirectView login(HttpServletResponse HttpServletResponse)
	{
		RedirectView redirectView = new RedirectView();

		// Redirects to the Auth DTU service
		// Returns the user with se2-webapp05.compute.dtu.dk/redirect?ticket=[TICKET]
		redirectView.setUrl("https://auth.dtu.dk/dtu/?service=https%3A%2F%2Fse2%2Dwebapp05%2Ecompute%2Edtu%2Edk%3A8443%2Fmazemap%2Fredirect");

		return redirectView;
	}

	// @author s154666
	@GetMapping(value="/redirect")
	public String redirect(@RequestParam("ticket") String ticket, HttpSession httpSession,Model model, HttpServletRequest request) throws MalformedURLException, IOException
	{
		return LoginService.redirectService(ticket,httpSession,model,request);
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
		return "index";
	}

	@GetMapping("/login_testing")
	public String loginTesting(@RequestParam String studentnr,
		HttpSession httpSession,Model model, HttpServletRequest request)
	{
		User foundUser = null;
		foundUser = userRepository.findUserByStudentnr(studentnr);
		//name = foundUser.getName();
		//login = true;
		this.user = foundUser;
		saveUserInSession(httpSession);
		return "loginsuccesful";
	}
}
