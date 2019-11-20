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


@RequestMapping("/survey")
@RestController
public class SurveyController {
    @Autowired
    private FilteredUserRepository userRepository;

    @Autowired
    private

    @PostMapping(value = "/save", headers = "Accept='application/json'")
    public String save(@RequestBody Survey survey) {






        if (!userRepository.findUsersByEmail(user.getEmail()).isEmpty()) {
            return String.format("A user with the email %s already exists!", user.getEmail());
        }
        try {
            Set<Role> roleSet = new HashSet<Role>();
            for (Role role : user.getRoles()) {
                roleSet.add(roleRepository.findById(role.getId()).get());
            }
            user.setRoles(roleSet);
            userRepository.save(user);
            return "You have been signed up!";
        } catch (Exception e) {
            e.printStackTrace();
            return "There was an error! User was not created! Please Try again! " + user.toString();
        }
    }