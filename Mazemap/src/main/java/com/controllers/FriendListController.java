package com.controllers;

import java.util.HashSet;
import java.util.Set;
import com.models.*;
import com.repositories.*;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends/")
public class FriendListController {
    @Autowired
    private FilteredUserRepository userRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Set<User> getAllFriends(@SessionAttribute("user") User user) {
        //User[] userArray;
        if (user == null)
           {
               try{
               throw new Exception("Stuff dont work");
               }
               catch(Exception e)
               {
                   e.printStackTrace();
                   return new HashSet<User>();
               }
           }
        //    Set<User> bs =  new HashSet<User>();
        //    bs.add(user);
        // return bs;
       Set<User> stuff = userRepository.findUserByEmail(user.getEmail()).getFriends();
       //stuff.add(user);
       return stuff;
    }

    @RequestMapping(value = "/allByEmail", method = RequestMethod.GET)
    public Set<User> getAllFriendsByEmail(@RequestParam("email") String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
        {
               try{
               throw new Exception("Stuff dont work");
               }
               catch(Exception e)
               {
                   e.printStackTrace();
                   return null;
               }
        }
        return user.getFriends();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addFriend(@SessionAttribute("user") User currentUser,
            @RequestBody String friendEmail) {

        if (friendEmail==null || friendEmail.isEmpty()||userRepository.findUsersByEmail(friendEmail).isEmpty())
            return String.format("The user with email %s does not exist!", friendEmail);

        User friend = userRepository.findUsersByEmail(friendEmail).get(0);

        if (friend != null && currentUser != null) {
            try {
                User u = userRepository.findUserByEmail(currentUser.getEmail());
                u.getFriends();
                friend.getFriends();
                u.setFriend(friend);
                userRepository.save(u);
                return String.format("A friend request was sent to user with email %s!", friendEmail);
            } catch (Exception e) {
                e.printStackTrace();
                return "There was an error and the friend request could not be sent!";
            }
        } else {
            return "There was an error and the friend request could not be sent! Try again!";
        }
    }
}