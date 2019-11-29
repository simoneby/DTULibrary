package com.controllers;

import java.util.HashSet;
import java.util.Set;

import com.helpers.ReturnMessageHelper;
import com.models.User;
import com.repositories.FilteredUserRepository;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Set<User> getAllFriends(@SessionAttribute("user") User user) {
        // User[] userArray;
        if (user == null) {
            try {
                throw new Exception("Stuff dont work");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        // Set<User> bs = new HashSet<User>();
        // bs.add(user);
        // return bs;
        Set<User> stuff = userRepository.findUserByEmail(user.getEmail()).getFriends();
        // stuff.add(user);
        return stuff;
    }

    @RequestMapping(value = "/receivedFriendRequests", method = RequestMethod.GET)
    public Set<User> getFriendRequests(@SessionAttribute("user") User user) {
        // User[] userArray;
        if (user == null) {
            try {
                throw new Exception("Stuff dont work");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        Set<User> stuff = userRepository.findUserByEmail(user.getEmail()).getReceivedFriendRequests();
        return stuff;
    }

    @RequestMapping(value = "/sentFriendRequests", method = RequestMethod.GET)
    public Set<User> getSentFriendRequests(@SessionAttribute("user") User user) {
        // User[] userArray;
        if (user == null) {
            try {
                throw new Exception("Stuff dont work");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        Set<User> stuff = userRepository.findUserByEmail(user.getEmail()).getSentFriendRequests();
        return stuff;
    }

    @RequestMapping(value = "/allByEmail", method = RequestMethod.GET)
    public Set<User> getAllFriendsByEmail(@RequestParam("email") String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            try {
                throw new Exception("Stuff dont work");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return user.getFriends();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addFriend(@SessionAttribute("user") User currentUser, @RequestBody String friendEmail) {
        String returnMessage;
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUser != null) {
                User u = userRepository.findUserByEmail(currentUser.getEmail());
                // u.getFriends();
                if (u.getFriends().contains(friend))
                    returnMessage = "That person is already part of your friend list!";
                else {
                    try {

                        friend.getFriends();
                        u.setFriend(friend);
                        userRepository.save(u);
                        returnMessage = String.format("A friend request was sent to user with email %s!", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the friend request could not be sent!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
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