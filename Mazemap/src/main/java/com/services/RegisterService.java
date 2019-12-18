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
import com.helpers.RedirectWrapper;

//@Author s154666
@SessionAttributes("user")
@Component
public class RegisterService
{
	@Autowired
	private FilteredUserRepository userRepository;

	public void setName(User user, String name)
	{
		User entity = userRepository.findUserByStudentnr(user.getStudentnr());
		entity.setName(name);
		userRepository.save(entity);
	}

}