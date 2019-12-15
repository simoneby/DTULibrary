package com.controllers;

import java.util.HashSet;
import java.util.Set;

import com.helpers.ReturnMessageHelper;
import com.models.User;
import com.repositories.FilteredUserRepository;
import com.services.FriendListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
//@Author s192671
@RestController
@RequestMapping("/friends")
public class FriendListController {
    @Autowired
    private FilteredUserRepository userRepository;
    @Autowired
    private FriendListService friendService;
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Set<User> getAllFriends(@SessionAttribute("user") User user) {
        if(friendService == null)
        {
            try{
                
            throw new Exception("Service is null");
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return friendService.getAllFriends(user.getEmail());
    }

    @RequestMapping(value = "/receivedFriendRequests", method = RequestMethod.GET)
    public Set<User> getFriendRequests(@SessionAttribute("user") User user) {
        return friendService.getReceivedFriendRequests(user.getEmail());
    }

    @RequestMapping(value = "/sentFriendRequests", method = RequestMethod.GET)
    public Set<User> getSentFriendRequests(@SessionAttribute("user") User user) {
        return friendService.getSentFriendRequests(user.getEmail());
    }

    @RequestMapping(value = "/allByEmail", method = RequestMethod.GET)
    public Set<User> getAllFriendsByEmail(@RequestParam("email") String email) {
        return friendService.getAllFriends(email);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addFriend(@SessionAttribute("user") User currentUser, @RequestBody String friendEmail) {

        String returnMessage = friendService.addFriend(currentUser.getEmail(), friendEmail);
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }

    @RequestMapping(value = "/acceptFriendRequest", method = RequestMethod.POST)
    public String acceptFriendRequest(@SessionAttribute("user") User currentUser, @RequestBody String friendEmail) {
        String returnMessage = friendService.acceptFriendRequest(currentUser.getEmail(), friendEmail);
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
  
    @RequestMapping(value = "/rejectFriendRequest", method = RequestMethod.POST)
    public String rejectFriendRequest(@SessionAttribute("user") User currentUser, @RequestBody String friendEmail) {
        String message = friendService.rejectFriendRequest(currentUser.getEmail(), friendEmail);
        return ReturnMessageHelper.getReturnMessage(message);
    }
  
    @RequestMapping(value = "/deleteFriend", method = RequestMethod.DELETE)
    public String deleteFriend(@SessionAttribute("user") User currentUser,@RequestParam  String friendEmail) {
        String returnMessage = friendService.deleteFriend(currentUser.getEmail(), friendEmail);
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
}