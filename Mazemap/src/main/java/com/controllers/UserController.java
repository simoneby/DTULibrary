package com.controllers;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.models.*;
import com.repositories.*;

//@Author s191772
@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private FilteredUserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email, @RequestParam String studentnr) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setStudentnr(studentnr);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    @GetMapping(path="/user")
    public @ResponseBody Iterable<User> getUserByEmail( @RequestParam String email) {
        // This returns a JSON or XML with the users
        return userRepository.findUsersByEmail(email);
    }



    public User getUserByStudentnr(String studentnr) throws HibernateException
    {
        return userRepository.findUserByStudentnr(studentnr);
    }


}