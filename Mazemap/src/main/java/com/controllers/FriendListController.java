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
        String returnMessage;
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUser != null) {
                User u = userRepository.findUserByEmail(currentUser.getEmail());
                if(u.getFriends().contains(friend))
                    returnMessage = "That person is already part of your friend list!";
                else {
                    try {
                        u.setFriend(friend);
                        userRepository.save(u);
                        userRepository.save(friend);
                        returnMessage = String.format("Friend request from user %s was accepted!", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the operation failed!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
  
    @RequestMapping(value = "/rejectFriendRequest", method = RequestMethod.POST)
    public String rejectFriendRequest(@SessionAttribute("user") User currentUser, @RequestBody String friendEmail) {
        String returnMessage;
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUser != null) {
                User u = userRepository.findUserByEmail(currentUser.getEmail());
                if(!u.getReceivedFriendRequests().contains(friend))
                    returnMessage = "That person is not a part of your followers!";
                else {
                    try {
                        u.removeFollower(friendEmail);
                        friend.removeFollowing(u.getEmail());
                        userRepository.save(u);
                        userRepository.save(friend);
                        returnMessage = String.format("Friend request from user with email %s was rejected", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the operation failed!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
  
    @RequestMapping(value = "/deleteFriend", method = RequestMethod.DELETE)
    public String deleteFriend(@SessionAttribute("user") User currentUser,@RequestParam  String friendEmail) {
        String returnMessage;
        if (friendEmail == null || friendEmail.isEmpty() )
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUserByEmail(friendEmail);
            //User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUser != null) {
                User u = userRepository.findUserByEmail(currentUser.getEmail());
                    try {
                        u.removeFriendFromFollowingByEmail(friendEmail);
                        friend.removeFriendFromFollowerByEmail(u.getEmail());
                        userRepository.save(u);
                        userRepository.save(friend);
                        returnMessage = String.format("Friend %s was removed from your list!", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error when removing your friend! Check the email you input!";
                    }

            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
}